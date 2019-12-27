package com.example.guardianpolitico;

public class News {
    /**
     * title of the article
     */
    private String mTitle;
    /**
     * url link to the article
     */
    private String mUrl;

    /**
     * section where the article belongs
     */
    private String mArticleSection;
    /**
     * date published
     */
    private String mDate;

      // publisher name/author
    private String mAuthor;



    /**
     *
     * Create a new Info object.
     *
     * @param title   is the title of article
     * @param section is the section where the article belongs
     * @param date    this is the date article was published
     */
    public News(String title, String section, String date, String url,String author) {
        mTitle = title;
        mArticleSection = section;
        mDate = date;
        mUrl = url;
        mAuthor = author;
    }

    /**
     * Get the title of the article
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the section where article belongs
     */
    public String getSection() {
        return mArticleSection;
    }

    /**
     * Get the date published
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Get the url of the article
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Get the author of the article
     */
    public String getAuthor() {
        return mAuthor;
    }
}
