package enums;

public enum QuestCategoryEnum {

    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final int categoryId;

    QuestCategoryEnum(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

}
