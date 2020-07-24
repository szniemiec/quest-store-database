//import daos.Creep.CreepDAO;
//import models.users.Creep;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class CreepDAOImplTest {
//
//    Statement statementMock;
//    Connection connectionMock;
//    ResultSet resultSetMock;
//
//    @BeforeEach
//    void setUp() {
//        statementMock = mock(Statement.class);
//        connectionMock = mock(Connection.class);
//        resultSetMock = mock(ResultSet.class);
//    }
//
//    @Test
//    void testIfGetAllTeamsCreateTeamListProperly() throws SQLException {
//        //Given
//        when(connectionMock.createStatement()).thenReturn(statementMock);
//        CreepDAO creepDAO = new CreepDAO(connectionMock) {
//            @Override
//            public Creep getCreep(int id) throws SQLException {
//                return null;
//            }
//
//            @Override
//            public List<Creep> getCreeps() throws SQLException, Exception {
//                return null;
//            }
//
//            @Override
//            public void deleteCreep(int id) {
//
//            }
//        };
//        setMocksBehaviour(statementMock, resultSetMock);
//
//        ArrayList<String> expected = new ArrayList<>();
//        expected.add("1;bc");
//        expected.add("2;ba");
//
//        //When
//        List<Creep> actual = null;
//        try {
//            actual = creepDAO.getCreeps();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //Then
//        assertEquals(expected, actual);
//    }
//
//    private void setMocksBehaviour(Statement statementMock, ResultSet resultSetMock) throws SQLException {
//        String query = "SELECT * FROM teams;";
//        when(statementMock.executeQuery(query)).thenReturn(resultSetMock);
//        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//        when(resultSetMock.getString("id")).thenReturn("1").thenReturn("2");
//        when(resultSetMock.getString("team_name")).thenReturn("bc").thenReturn("ba");
//    }
//}