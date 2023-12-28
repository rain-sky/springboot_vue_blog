package com.cern.constants;


// 文章相关常量在此处定义
public class SystemConstants {
    /**
     *  文章处于草稿未发布状态
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     *  文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 文章列表当前查询页数
     */
    public static final int ARTICLE_STATUS_CURRENT = 1;

    /**
     * 文章列表每页显示的数据条数
     */
    public static final int ARTICLE_STATUS_SIZE = 10;
    /**
     * 分类表的分类状态是正常状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /**
     * 友链状态为审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";
    /**
     * 评论区的某条评论是根评论
     */
    public static final String COMMENT_ROOT = "-1";
    /**
     * 文章的评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 友链的评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 分类表的分类状态是正常状态
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 权限类型，菜单
     */
    public static final String TYPE_MENU = "C";

    /**
     * 权限类型，按钮
     */
    public static final String TYPE_BUTTON = "F";

    /**
     * 权限类型，目录
     */
    public static final Long PARENT_MENU_TYPE = 0L;
}