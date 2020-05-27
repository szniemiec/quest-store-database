package daos.quest;

import database.PostgreSQLJDBC;
import enums.QuestCategory;
import models.Quest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestDAOImpl implements QuestDAO {
    private PostgreSQLJDBC postgreSQLJDBC;
    private List<Quest> quests;
    private Connection c;
    private Statement statement;

    private QuestDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public Quest getQuest(int id) {
        return null;
    }

    @Override
    public void deleteQuest(int id){
            String sql = "DELETE FROM Products WHERE Id = " + id + ";";
            sqlStatement(sql);
    }

    @Override
    public List<Quest> getQuests() throws SQLException {
        Statement st = postgreSQLJDBC.getConnection().createStatement();

        List<Quest> quests = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM \"Quests\" ");
            quests = createQuestList(rs);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return quests;
    }

    private List<Quest> createQuestList(ResultSet rs) throws Exception {
        List<Quest> quests = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("Name");
            String description = rs.getString("Description");
            int reward = rs.getInt("Reward");
            int categoryId = rs.getInt("category_id");
            QuestCategory questCategory = questCategoryToEnum(categoryId);

            Quest quest = new Quest(id, name, description, reward, questCategory);
            quests.add(quest);
        }
        return quests;
    }

    private QuestCategory questCategoryToEnum(int categoryId) throws Exception {
        QuestCategory questCategory;
        switch (categoryId) {
            case 1:
                questCategory = QuestCategory.EASY;
                break;
            case 2:
                questCategory = QuestCategory.MEDIUM;
                break;
            case 3:
                questCategory = QuestCategory.HARD;
                break;
            default:
                throw new Exception("Wrong module id");
        }
        return questCategory;
    }

    @Override
    public void addNewQuest(String name, String description, QuestCategory category, int reward) {
        int id = getIdInOrder();
        String sql = "INSERT INTO Quests (id,Name, Description,Reward,category_id)" + "VALUES ( " + id + "," + name + ",'" +
                description + "','" + category + "'," + reward + ");";
        sqlStatement(sql);
        System.out.println("New product added!");
    }

    private void sqlStatement(String sql) {
        PostgreSQLJDBC connector = new PostgreSQLJDBC();
        c = connector.getConnection();
        try {
            statement = c.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getIdInOrder() {
        int result = 1;
        for (Quest quest : quests) {
            int productId = quest.getId();
            if (productId > result) {
                result = productId;
            }
        }
        return ++result;
    }
}
