package com.artemkopan.domain.utils

class Pagination : ListIterator<Int> {
    companion object {
        private const val DEFAULT = 1
    }

    var page = DEFAULT
    var total = DEFAULT

    fun reset() {
        page = DEFAULT
    }

    override fun hasNext() = page < total

    override fun hasPrevious() = page > DEFAULT

    override fun next(): Int = ++page

    override fun nextIndex(): Int {
        throw NotImplementedError()
    }

    override fun previous(): Int = --page

    override fun previousIndex(): Int {
        throw NotImplementedError()
    }

    override fun toString(): String {
        return "Pagination(page=$page, total=$total)"
    }


}