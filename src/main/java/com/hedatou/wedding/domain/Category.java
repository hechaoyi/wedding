package com.hedatou.wedding.domain;

public enum Category {

    MALE_FAMILY("male_family"), // 新郎家人
    FEMALE_FAMILY("female_family"), // 新娘家人
    NEU_CLASSMATE("neu_classmate"), // 东大同学
    MALE_CLASSMATE("male_classmate"), // 新郎同学
    FEMALT_WORKMATE("female_workmate"), // 新娘同事
    MALE_FATHER_FRIEND("male_father_friend"), // 新郎父亲朋友
    MALE_MOTHER_FRIEND("male_mother_friend"), // 新郎母亲朋友
    OTHER("other"); // 其他

    private final String value;

    private Category(String value) {
        this.value = value;
    }

    public static Category create(String name) {
        for (Category category : Category.values())
            if (category.value.equals(name))
                return category;
        return null;
    }

}
