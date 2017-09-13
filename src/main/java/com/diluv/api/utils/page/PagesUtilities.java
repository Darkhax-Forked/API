package com.diluv.api.utils.page;


public class PagesUtilities {

    public static Page getPageDetails(String inputPage, String inputPerPage, int count) {
        int perPage = toIntOrDefaultValue(inputPerPage, 10);

        if (perPage < 5) {
            perPage = 5;
        } else if (perPage > 25) {
            perPage = 25;
        }

        int totalPageCount = count / perPage;
        if (count % perPage > 0)
            totalPageCount++;

//    val inputOrder = req.getParam("order")
//    val inputOrderBy = req.getParam("orderBy")

        int page = toIntOrDefaultValue(inputPage, 1);


        //TODO Or error request back?
        if (page > totalPageCount) {
            page = totalPageCount;
            //TODO Or error request back?
        }

        if (page < 1) {
            page = 1;
        }

        return new Page(page, (page - 1) * perPage, perPage, totalPageCount);
    }


    public static int toIntOrDefaultValue(String inputValue, int defaultValue) {
        try {
            return Integer.parseInt(inputValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}