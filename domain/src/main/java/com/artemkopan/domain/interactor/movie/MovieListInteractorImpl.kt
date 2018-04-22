package com.artemkopan.domain.interactor.movie

import com.artemkopan.domain.Const
import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.domain.entity.PaginationEntity
import com.artemkopan.domain.interactor.movie.MovieSort.POPULAR
import com.artemkopan.domain.interactor.movie.MovieSort.TOP_RATED
import com.artemkopan.domain.repository.KeyRepository
import com.artemkopan.domain.repository.ResourceRepository
import com.artemkopan.domain.repository.movie.MovieRepository
import com.artemkopan.domain.utils.Logger
import com.artemkopan.domain.utils.Pagination
import com.artemkopan.domain.utils.UiState
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MovieListInteractorImpl @Inject constructor(private val repository: MovieRepository,
                                                  private val keyRepository: KeyRepository,
                                                  private val resourceRepository: ResourceRepository)
    : MovieListInteractor {

    private lateinit var sort: MovieSort
    private var pagination: Pagination = Pagination()
    private var loadDisposable: Disposable? = null

    private val subject = BehaviorSubject.create<UiState<List<MovieEntity>>>()
    private val items = arrayListOf<MovieEntity>()

    private val key by lazy { keyRepository.getApiKey() }
    private val lang by lazy { getCurrentLanguage() }

    override fun setSort(sort: MovieSort) {
        this.sort = sort
    }

    override fun observer(): Observable<UiState<List<MovieEntity>>> = subject

    override fun loadPage() {
        loadDisposable?.dispose()
        loadDisposable = Single.fromCallable { pagination.page }
                .subscribeOn(Schedulers.io())
                .flatMap(getObservableBySort())
                .doOnSuccess(onListLoadedSuccess())
                .map { it.results ?: Collections.emptyList() }
                .doOnSubscribe { subject.onNext(UiState(true)) }
                .doOnError {
                    Logger.e(Const.Tag.MOVIE_LIST, "Error load movie list: sort $sort, " +
                            "pagination $pagination", it)
                }
                .subscribe({ movies->
                    items.addAll(movies)
                    val result = ArrayList(items)
                    subject.onNext(UiState(result))
                }, {throwable ->
                    subject.onNext(UiState(throwable))
                })
    }

    override fun nextPage() {
        if (pagination.hasNext()) {
            pagination.next()
            loadPage()
        } else {
            Logger.d("It is the last page $pagination")
        }
    }

    override fun dispose() {
        loadDisposable?.dispose()
    }

    private fun onListLoadedSuccess(): (PaginationEntity<MovieEntity>) -> Unit {
        return {
            pagination.page = it.page
            pagination.total = it.totalPages
        }
    }

    private fun getCurrentLanguage(): String {
        val currentLanguage = resourceRepository.getCurrentLocale()?.language
        return if (currentLanguage.isNullOrBlank()) {
            currentLanguage!!
        } else {
            Locale.US.language
        }
    }

    private fun getObservableBySort(): (Int) -> Single<PaginationEntity<MovieEntity>> {
        return { page ->
            when (sort) {
                POPULAR -> repository.getPopular(key, page, lang)
                TOP_RATED -> repository.getTopRated(key, page, lang)
            }
        }
    }
}