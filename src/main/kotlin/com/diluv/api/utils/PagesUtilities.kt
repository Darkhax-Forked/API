package com.diluv.api.utils

fun getPageDetails(inputPage: String?, inputPerPage: String?, count: Int, callback: (page: Int, offset: Int, perPage: Int, totalPageCount: Int) -> Any) {
    var perPage = toIntOrDefaultValue(inputPerPage, 10)

    if (perPage < 5) {
        perPage = 5
    } else if (perPage > 25) {
        perPage = 25
    }

    var totalPageCount: Int = count / perPage
    if (count % perPage > 0)
        totalPageCount++

//    val inputOrder = req.getParam("order")
//    val inputOrderBy = req.getParam("orderBy")

    var page = toIntOrDefaultValue(inputPage, 1)


    //TODO Or error request back?
    if (page > totalPageCount) {
        page = totalPageCount
        //TODO Or error request back?
    }

    if (page < 1) {
        page = 1
    }
    callback(page, (page - 1) * perPage, perPage, totalPageCount)
}


fun toIntOrDefaultValue(inputValue: String?, defaultValue: Int): Int {
    if (inputValue != null) {
        val value = inputValue.toIntOrNull()
        if (value != null)
            return value
    }
    return defaultValue
}