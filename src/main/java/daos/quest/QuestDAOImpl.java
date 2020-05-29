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
    public void addQuest(Quest quest) {
        final String INSERT_SQL = "INSERT INTO \"Quests\" (\"Name\", \"Description\", \"Reward\", \"category_id\")" +
                "VALUES (?, ?, ?, ?);";

        String name = quest.getName();
        String description = quest.getDescription();
        int reward = quest.getReward();
        QuestCategoryEnum questCategoryEnum = quest.getQuestCategoryEnum();

        PreparedStatement ps = null;

        try {
            ps = this.postgreSQLJDBC.getConnection().prepareStatement(INSERT_SQL);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, reward);
            ps.setInt(4, questCategoryEnum.getCategoryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public void editQuest(Quest quest) {
        final String UPDATE_SQL = "UPDATE \"Quests\"" +
                "SET \"Name\" = ?, \"Description\" = ?, \"Reward\" = ?, category_id = ?" +
                "WHERE id = ?;";

        try {
            PreparedStatement ps = this.postgreSQLJDBC.getConnection().prepareStatement(UPDATE_SQL);
            ps.setString(1, quest.getName());
            ps.setString(2, quest.getDescription());
            ps.setInt(3, quest.getReward());
            ps.setInt(4, quest.getQuestCategoryEnum().getCategoryId());
            ps.setInt(5, quest.getId());
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

            Quest quest = new Quest(id, name, description, reward, questCategoryEnum);

            questList.add(quest);
        }

        return questList;
    }

    private QuestCategoryEnum categoryIdToEnum(int categoryId) throws Exception {
        QuestCategoryEnum questCategoryEnum;
        if (categoryId == 1) {
            questCategoryEnum = QuestCategoryEnum.EASY;
        } else if (categoryId == 2) {
            questCategoryEnum = QuestCategoryEnum.MEDIUM;
        } else if (categoryId == 3) {
            questCategoryEnum = QuestCategoryEnum.HARD;
        } else {
            throw new Exception("Wrong quest category");
        }
        return questCategoryEnum;
    }

}
