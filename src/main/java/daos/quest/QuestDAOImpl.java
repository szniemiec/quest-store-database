package daos.quest;

import database.PostgreSQLJDBC;
import enums.QuestCategoryEnum;
import models.Quest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestDAOImpl implements QuestDAO {

    private PostgreSQLJDBC postgreSQLJDBC;

    public QuestDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public List<Quest> getQuests() throws SQLException {
        final String SELECT_SQL = "SELECT * FROM \"Quests\";";

        Statement st = postgreSQLJDBC.getConnection().createStatement();

        List<Quest> questList = new ArrayList<>();

        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            questList = createQuestList(rs);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return questList;
    }

    @Override
    public Quest getQuest(int id) throws SQLException {
        final String SELECT_SQL = "SELECT * FROM \"Quests\" WHERE id = '" + id + "';";

        Statement st = postgreSQLJDBC.getConnection().createStatement();

        List<Quest> questList = new ArrayList<>();

        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            questList = createQuestList(rs);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return questList.get(0);
    }

    @Override
    public void deleteQuest(int id) {
        final String DELETE_SQL = "DELETE FROM \"Quests\" WHERE id = ?;";

        try {
            PreparedStatement ps = this.postgreSQLJDBC.getConnection().prepareStatement(DELETE_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Quest> createQuestList(ResultSet rs) throws Exception {
        List<Quest> questList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            int categoryId = rs.getInt("category_id");
            QuestCategoryEnum questCategoryEnum = categoryIdToEnum(categoryId);
            int reward = rs.getInt("reward");

            Quest quest = new Quest(id, name, description, questCategoryEnum, reward);

            questList.add(quest);
        }

        return questList;
    }

    private QuestCategoryEnum categoryIdToEnum(int categoryId) throws Exception {
        QuestCategoryEnum questCategoryEnum;
        switch (categoryId) {
            case 1 -> questCategoryEnum = QuestCategoryEnum.EASY;
            case 2 -> questCategoryEnum = QuestCategoryEnum.MEDIUM;
            case 3 -> questCategoryEnum = QuestCategoryEnum.HARD;
            default -> throw new Exception("Wrong quest category");
        }
        return questCategoryEnum;
    }

}
