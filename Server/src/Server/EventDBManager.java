package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EventDBManager {
    //получение информации о мероприятии по id
    public static HashMap<String,String> getEventById(int event_id) throws SQLException {
        HashMap<String,String> event = new HashMap<String,String>();
        Connection conn = DBManager.getInstance().getConn();
        Statement stmt = conn.createStatement();
        ResultSet event_query = stmt.executeQuery( "SELECT * FROM public.event WHERE event_id = " + event_id);

        while (event_query.next()) {
            event.put("name", event_query.getString("event_name"));
            event.put("subject", event_query.getString("subject"));
            event.put("date", event_query.getString("date"));
            event.put("place", event_query.getString("place"));
            event.put("type", Queries.getEventTypeName(event_query.getInt("event_type_id")));
            event.put("genre", Queries.getGenreName(event_query.getInt("genre_id")));
            event.put("description", event_query.getString("description"));
            event.put("program", event_query.getString("program"));
        }
        event_query.close();
        stmt.close();
        return event;
    }

    //добавление мероприятия в БД
    public static int addEventInDB(HashMap<String, String> event) throws SQLException {
        Connection conn = DBManager.getInstance().getConn();
        String sql = "INSERT INTO public.event (event_name,subject,date,place,event_type_id,genre_id,description,program) "
                + "VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, event.get("name"));
        if (!Objects.equals(event.get("subject"), "")) {
            pstmt.setString(2, event.get("subject"));
        } else {
            pstmt.setNull(2, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.get("date"), "")) {
            pstmt.setString(3, event.get("date"));
        } else {
            pstmt.setNull(3, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.get("place"), "")) {
            pstmt.setString(4, event.get("place"));
        } else {
            pstmt.setNull(4, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.get("type"), "Не выбрано")) {
            pstmt.setInt(5, Queries.getEventTypeId(event.get("type")));
        } else {
            pstmt.setNull(5, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.get("genre"), "Не выбрано")) {
            pstmt.setInt(6, Queries.getGenreId(event.get("genre")));
        } else {
            pstmt.setNull(6, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.get("description"), "")) {
            pstmt.setString(7, event.get("description"));
        } else {
            pstmt.setNull(7, java.sql.Types.NULL);
        }
        if (!Objects.equals(event.get("program"), "")) {
            pstmt.setString(8, event.get("program"));
        } else {
            pstmt.setNull(8, java.sql.Types.NULL);
        }
        pstmt.executeUpdate();
        ResultSet keys_query = pstmt.getGeneratedKeys();
        int event_id = 0;
        while (keys_query.next()) {
            event_id = keys_query.getInt("event_id");
        }
        pstmt.close();
        return event_id;
    }

    //удаление мероприятий из БД
    public static void deleteEventFromDB(ArrayList<Integer> deletedEvents) throws SQLException {
        for (Integer event_id : deletedEvents) {
            Connection conn = DBManager.getInstance().getConn();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM public.event WHERE event_id = " + event_id;
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }
}