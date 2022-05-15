package com.example.alm_gui;
import com.example.alm_gui.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class PostgreConnection {
    private String url="jdbc:postgresql://localhost:5432/ALM";
    private String user="postgres";
    private String password="admin";
    public PostgreConnection(){
        try (Connection connection = DriverManager.getConnection(url,user,password)) {
            System.out.println("Connected to PostgreSQL database!");

        }  catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
    public long insertItem(Item item){
        String SQL = "INSERT INTO public.item(time_create, type, modify_item)" + "VALUES (?, ?, ?)";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setTimestamp(1, Timestamp.valueOf(item.getTime_create()));
                pstmt.setInt(2, item.getType());
                pstmt.setTimestamp(3, Timestamp.valueOf(item.getModify_item()));
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                        try (ResultSet rs = pstmt.getGeneratedKeys()) {
                            if (rs.next()) {
                                id = rs.getLong(1);
                            }
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public long insertUser(User user1){
        String SQL = "INSERT INTO public.user(login, password, position)" + "VALUES (?, ?, ?)";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user1.getLogin());
            pstmt.setString(2, user1.getPassword());
            pstmt.setString(3, user1.getPosition());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public long insertRequirement(Requirement req){
        String SQL = "INSERT INTO public.requirement(id_item, description, analysis_estimate, development_estimate, testing_estimate, release_date, time, title, version, state, assign, changed_by)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, req.getId_item());
            pstmt.setString(2, req.getDescription());
            if (!Objects.equals(req.getAnalysis_estimate(), "") && !Objects.equals(req.getAnalysis_estimate(), null))
                pstmt.setTimestamp(3, Timestamp.valueOf(req.getAnalysis_estimate()));
            else pstmt.setNull(3,Types.TIMESTAMP);

            if (!Objects.equals(req.getDevelopment_estimate(), "") && !Objects.equals(req.getAnalysis_estimate(), null))
                pstmt.setTimestamp(4, Timestamp.valueOf(req.getDevelopment_estimate()));
            else pstmt.setNull(4,Types.TIMESTAMP);

            if (!Objects.equals(req.getTesting_estimate(), "") && !Objects.equals(req.getAnalysis_estimate(), null))
                pstmt.setTimestamp(5, Timestamp.valueOf(req.getTesting_estimate()));
            else pstmt.setNull(5,Types.TIMESTAMP);

            if (!Objects.equals(req.getRelease_date(), "") && !Objects.equals(req.getAnalysis_estimate(), null))
                pstmt.setTimestamp(6,Timestamp.valueOf(req.getRelease_date()));
            else pstmt.setNull(6,Types.TIMESTAMP);

            pstmt.setTimestamp(7,Timestamp.valueOf(req.getTime()));
            pstmt.setString(8, req.getTitle());
            pstmt.setString(9,req.getVersion());
            pstmt.setString(10, req.getState());
            pstmt.setInt(11, req.getAssign());
            pstmt.setInt(12, req.getChanged_by());



            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
   public long insertBug(Bug bug){
        String SQL = "INSERT INTO public.bug(id_item, steps, environment, found_build, integreted_build, os_ver, verified, dev, how_found, localization, priority, severity, time, changed_by, title, version, state, assign)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, bug.getId_item());
            pstmt.setString(2, bug.getSteps());
            pstmt.setString(3, bug.getEnvironment());
            pstmt.setString(4, bug.getFound_build());
            pstmt.setString(5, bug.getIntegreted_build());
            pstmt.setString(6,bug.getOs_ver());
            pstmt.setString(7,bug.getVerified());
            pstmt.setString(8,bug.getDev());
            pstmt.setString(9,bug.getHow_found());
            pstmt.setString(10,bug.getLocalization());
            pstmt.setInt(11,bug.getPriority());
            pstmt.setInt(12,bug.getSeverity());
            pstmt.setTimestamp(13,Timestamp.valueOf(bug.getTime()));
            pstmt.setInt(14, bug.getChanged_by());
            pstmt.setString(15, bug.getTitle());
            pstmt.setString(16,bug.getVersion());
            pstmt.setString(17, bug.getState());
            pstmt.setInt(18, bug.getAssign());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public long insertIssue(Issue issue){
        String SQL = "INSERT INTO public.issue(id_item, issue_type, found_build, steps, description, priority, time, changed_by, title, version, state, assign)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, issue.getId_item());
            pstmt.setString(2, issue.getIssue_type());
            pstmt.setString(3, issue.getFound_build());
            pstmt.setString(4, issue.getSteps());;
            pstmt.setString(5, issue.getDescription());
            pstmt.setInt(6,issue.getPriority());

            pstmt.setTimestamp(7,Timestamp.valueOf(issue.getTime()));
            pstmt.setInt(8, issue.getChanged_by());
            pstmt.setString(9, issue.getTitle());
            pstmt.setString(10,issue.getVersion());
            pstmt.setString(11, issue.getState());
            pstmt.setInt(12, issue.getAssign());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public long insertTask(Task task){
        String SQL = "INSERT INTO public.task(id_item, original_effort, remaining_effort, expected_resolve, resolve, description, dev, time, title, version, state, assign, changed_by)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, task.getId_item());
            pstmt.setDouble(2, task.getOriginal_effort());
            pstmt.setDouble(3, task.getRemaining_effort());
            if (!Objects.equals(task.getExpected_resolve(), "") && !Objects.equals(task.getExpected_resolve(), null))
                pstmt.setTimestamp(4, Timestamp.valueOf(task.getExpected_resolve()));
            else pstmt.setNull(4,Types.TIMESTAMP);

            if (!Objects.equals(task.getResolve(), "") && !Objects.equals(task.getExpected_resolve(), null))
                pstmt.setTimestamp(5, Timestamp.valueOf(task.getResolve()));
            else pstmt.setNull(5,Types.TIMESTAMP);

            pstmt.setString(6, task.getDescription());
            pstmt.setString(7, task.getDev());
            pstmt.setTimestamp(8,Timestamp.valueOf(task.getTime()));
            pstmt.setString(9, task.getTitle());
            pstmt.setString(10,task.getVersion());
            pstmt.setString(11, task.getState());
            pstmt.setInt(12, task.getAssign());
            pstmt.setInt(13, task.getChanged_by());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public long insertTestCase(TestCase testCase){
        String SQL = "INSERT INTO public.test_case(id_item, steps, priority, auto_status, dev, time, title, version, state, assign, changed_by)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, testCase.getId_item());
            pstmt.setString(2, testCase.getSteps());;
            pstmt.setInt(3,testCase.getPriority());
            pstmt.setString(4,testCase.getAuto_status());
            pstmt.setString(5,testCase.getDev());

            pstmt.setTimestamp(6,Timestamp.valueOf(testCase.getTime()));
            pstmt.setString(7, testCase.getTitle());
            pstmt.setString(8,testCase.getVersion());
            pstmt.setString(9, testCase.getState());
            pstmt.setInt(10, testCase.getAssign());
            pstmt.setInt(11, testCase.getChanged_by());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public long insertTestPlan(TestPlan testPlan){
        String SQL = "INSERT INTO public.test_plan(id_item, title, version, state, assign, changed_by, time)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, testPlan.getId_item());
            pstmt.setString(2, testPlan.getTitle());
            pstmt.setString(3,testPlan.getVersion());
            pstmt.setString(4, testPlan.getState());
            pstmt.setInt(5, testPlan.getAssign());
            pstmt.setInt(6, testPlan.getChanged_by());
            pstmt.setTimestamp(7,Timestamp.valueOf(testPlan.getTime()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public long insertLink(Link link){
        String SQL = "INSERT INTO public.link(id_item1, id_item2, link_type) " +
                "VALUES (?, ?, ?)";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, link.getId_item1());
            pstmt.setInt(2, link.getId_item2());
            pstmt.setString(3,link.getLink_type());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public long insertPlanCase(PlanCase planCase){
        String SQL = "INSERT INTO public.plan_case(id_test_plan, id_test_case, execution)" +
                "VALUES (?, ?, ?);";
        long id = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, planCase.getId_test_plan());
            pstmt.setInt(2, planCase.getId_test_case());
            pstmt.setString(3,planCase.getExecution());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public int deleteItem(long id) {
        String SQL = "DELETE FROM public.item WHERE id = ?";

        int affectedrows = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setLong(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }

    public int deleteLink(long id) {
        String SQL = "DELETE FROM public.link WHERE id_link = ?";

        int affectedrows = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setLong(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }

    public int deletePlanCase(long id) {
        String SQL = "DELETE FROM public.plan_case WHERE id_plan_case= ?";

        int affectedrows = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setLong(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }

    public int updateExecutionTest(int id, String execution) {
        String SQL = "UPDATE public.plan_case "+ "SET execution = ? WHERE id_plan_case = ?";

        int affectedrows = 0;
        Properties info = new Properties();
        info.setProperty("user",user);
        info.setProperty("password",password);
        info.setProperty("useUnicode","true");
        info.setProperty("characterEncoding","utf8");
        try (Connection connection = DriverManager.getConnection(url,info);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, execution);
            pstmt.setInt(2, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }

    public int updateModifyItem(int id, String time) {
        String SQL = "UPDATE public.item "+ "SET modify_item = ? WHERE id = ?";

        int affectedrows = 0;
        Properties info = new Properties();
        info.setProperty("user",user);
        info.setProperty("password",password);
        info.setProperty("useUnicode","true");
        info.setProperty("characterEncoding","utf8");
        try (Connection connection = DriverManager.getConnection(url,info);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(time));
            pstmt.setInt(2, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }
    public ObservableList<MainItem> getMainScreenItems() {

        String SQL = "SELECT * FROM public.\"MainScreenView\"";
        ObservableList<MainItem> mainItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                MainItem mainItem = new MainItem(rs.getInt("id"),
                        rs.getString("title"),rs.getString("time_create"),
                        rs.getString("modify_item"),rs.getString("Name"),
                        rs.getString("state"),rs.getString("login"));
                mainItems.add(mainItem);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mainItems;
    }
    public User findUser(int id) {

        String SQL = "SELECT * FROM public.User " +
                "WHERE id = ?";
        User u = new User();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    u.setId(id);
                    u.setLogin(rs.getString("login"));
                    u.setPassword(rs.getString("password"));
                    u.setPosition(rs.getString("position"));
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
    public User findUser(String login, String pass) {

        String SQL = "SELECT * FROM public.User " +
                "WHERE login = ? AND password = ?";
        User u = new User();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setString(1, login);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    u.setId(Integer.parseInt(rs.getString("id")));
                    u.setLogin(login);
                    u.setPassword(pass);
                    u.setPosition(rs.getString("position"));
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
    public User findUser(String login) {

        String SQL = "SELECT * FROM public.User " +
                "WHERE login = ?";
        User u = new User();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setString(1, login);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    u.setId(Integer.parseInt(rs.getString("id")));
                    u.setLogin(login);
                    u.setPassword(rs.getString("password"));
                    u.setPosition(rs.getString("position"));
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
    public Requirement findRequirement(int id_item, String time) {

        String SQL = "SELECT * FROM public.requirement " +
                "WHERE id_item = ? AND time = ?";
        Requirement requirement = new Requirement();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id_item);
            pstmt.setTimestamp(2, Timestamp.valueOf(time));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    requirement.setId(rs.getInt("id_requirement"));
                    requirement.setId_item(id_item);
                    requirement.setDescription(rs.getString("description"));
                    requirement.setAnalysis_estimate(rs.getString("analysis_estimate"));
                    requirement.setDevelopment_estimate(rs.getString("development_estimate"));
                    requirement.setTesting_estimate(rs.getString("testing_estimate"));
                    requirement.setRelease_date(rs.getString("release_date"));
                    requirement.setTime(time);
                    requirement.setTitle(rs.getString("title"));
                    requirement.setVersion(rs.getString("version"));
                    requirement.setState(rs.getString("state"));
                    requirement.setAssign(rs.getInt("assign"));
                    requirement.setChanged_by(rs.getInt("changed_by"));

                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return requirement;
    }

    public Bug findBug(int id_item, String time) {

        String SQL = "SELECT * FROM public.bug " +
                "WHERE id_item = ? AND time = ?";
        Bug bug = new Bug();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id_item);
            pstmt.setTimestamp(2, Timestamp.valueOf(time));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    bug.setId(rs.getInt("id_bug"));
                    bug.setId_item(id_item);
                    bug.setSteps(rs.getString("steps"));
                    bug.setTime(time);
                    bug.setTitle(rs.getString("title"));
                    bug.setVersion(rs.getString("version"));
                    bug.setState(rs.getString("state"));
                    bug.setAssign(rs.getInt("assign"));
                    bug.setChanged_by(rs.getInt("changed_by"));
                    bug.setEnvironment(rs.getString("environment"));
                    bug.setFound_build(rs.getString("found_build"));
                    bug.setIntegreted_build(rs.getString("integreted_build"));
                    bug.setOs_ver(rs.getString("os_ver"));
                    bug.setVerified(rs.getString("verified"));
                    bug.setHow_found(rs.getString("how_found"));
                    bug.setLocalization(rs.getString("localization"));
                    bug.setPriority(rs.getInt("priority"));
                    bug.setSeverity(rs.getInt("severity"));
                    bug.setDev(rs.getString("dev"));

                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return bug;
    }
    public Issue findIssue(int id_item, String time) {

        String SQL = "SELECT * FROM public.issue " +
                "WHERE id_item = ? AND time = ?";
         Issue issue = new Issue();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id_item);
            pstmt.setTimestamp(2, Timestamp.valueOf(time));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    issue.setId(rs.getInt("id_issue"));
                    issue.setId_item(id_item);
                    issue.setSteps(rs.getString("steps"));
                    issue.setTime(time);
                    issue.setTitle(rs.getString("title"));
                    issue.setVersion(rs.getString("version"));
                    issue.setState(rs.getString("state"));
                    issue.setAssign(rs.getInt("assign"));
                    issue.setChanged_by(rs.getInt("changed_by"));
                    issue.setIssue_type(rs.getString("issue_type"));
                    issue.setFound_build(rs.getString("found_build"));
                    issue.setDescription(rs.getString("description"));
                    issue.setPriority(rs.getInt("priority"));

                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return issue;
    }
    public Task findTask(int id_item, String time) {

        String SQL = "SELECT * FROM public.task " +
                "WHERE id_item = ? AND time = ?";
        Task task = new Task();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id_item);
            pstmt.setTimestamp(2, Timestamp.valueOf(time));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    task.setId(rs.getInt("id_task"));
                    task.setId_item(id_item);
                    task.setTime(time);
                    task.setTitle(rs.getString("title"));
                    task.setVersion(rs.getString("version"));
                    task.setState(rs.getString("state"));
                    task.setAssign(rs.getInt("assign"));
                    task.setChanged_by(rs.getInt("changed_by"));
                    task.setOriginal_effort(rs.getDouble("original_effort"));
                    task.setRemaining_effort(rs.getDouble("remaining_effort"));
                    task.setDescription(rs.getString("description"));
                    task.setExpected_resolve(rs.getString("expected_resolve"));
                    task.setResolve(rs.getString("resolve"));
                    task.setDev(rs.getString("dev"));

                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return task;
    }
    public TestCase findTestCase(int id_item, String time) {

        String SQL = "SELECT * FROM public.test_case " +
                "WHERE id_item = ? AND time = ?";
        TestCase testCase = new TestCase();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id_item);
            pstmt.setTimestamp(2, Timestamp.valueOf(time));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    testCase.setId(rs.getInt("id_case"));
                    testCase.setId_item(id_item);
                    testCase.setTime(time);
                    testCase.setTitle(rs.getString("title"));
                    testCase.setVersion(rs.getString("version"));
                    testCase.setState(rs.getString("state"));
                    testCase.setAssign(rs.getInt("assign"));
                    testCase.setChanged_by(rs.getInt("changed_by"));
                    testCase.setPriority(rs.getInt("priority"));
                    testCase.setAuto_status(rs.getString("auto_status"));
                    testCase.setSteps(rs.getString("steps"));
                    testCase.setDev(rs.getString("dev"));

                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return testCase;
    }
    public TestCase findTestCase(int id_item) {

        String SQL = "SELECT * FROM public.test_case " +
                "WHERE id_item = ? order by time desc " +
                "limit 1";
        TestCase testCase = new TestCase();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id_item);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    testCase.setId(rs.getInt("id_case"));
                    testCase.setId_item(id_item);
                    testCase.setTime(rs.getString("time"));
                    testCase.setTitle(rs.getString("title"));
                    testCase.setVersion(rs.getString("version"));
                    testCase.setState(rs.getString("state"));
                    testCase.setAssign(rs.getInt("assign"));
                    testCase.setChanged_by(rs.getInt("changed_by"));
                    testCase.setPriority(rs.getInt("priority"));
                    testCase.setAuto_status(rs.getString("auto_status"));
                    testCase.setSteps(rs.getString("steps"));
                    testCase.setDev(rs.getString("dev"));

                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return testCase;
    }
    public TestPlan findTestPlan(int id_item, String time) {

        String SQL = "SELECT * FROM public.test_plan " +
                "WHERE id_item = ? AND time = ?";
        TestPlan testPlan = new TestPlan();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id_item);
            pstmt.setTimestamp(2, Timestamp.valueOf(time));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    testPlan.setId(rs.getInt("id_test_plan"));
                    testPlan.setId_item(id_item);
                    testPlan.setTime(time);
                    testPlan.setTitle(rs.getString("title"));
                    testPlan.setVersion(rs.getString("version"));
                    testPlan.setState(rs.getString("state"));
                    testPlan.setAssign(rs.getInt("assign"));
                    testPlan.setChanged_by(rs.getInt("changed_by"));
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return testPlan;
    }
    public ObservableList<TestCaseItem> getTestCaseItems(int id) {

        String SQL = "SELECT id_item,title,execution FROM public.\"TestPlanView\" " +
                "WHERE id_test_plan = ?";
        ObservableList<TestCaseItem> testCaseItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TestCaseItem testCaseItem = new TestCaseItem(id,rs.getInt("id_item"),
                        rs.getString("title"), rs.getString("execution"));
                testCaseItems.add(testCaseItem);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return testCaseItems;
    }

    public PlanCase findPlanCase(int id_test_plan, int id_test_case) {

        String SQL = "SELECT * FROM public.plan_case " +
                "WHERE id_test_plan = ? AND id_test_case = ?";
        PlanCase pc = new PlanCase();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id_test_plan);
            pstmt.setInt(2,id_test_case);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    pc.setId(rs.getInt("id_plan_case"));
                    pc.setId_test_plan(id_test_plan);
                    pc.setId_test_case(id_test_case);
                    pc.setExecution(rs.getString("execution"));
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pc;
    }
    public ObservableList<LinkItem> getLinkItems(int id) {

        String SQL = "SELECT * FROM public.Link "+"WHERE id_item1 = ? or id_item2 = ?";
        ObservableList<LinkItem> linkItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL))
            {
            pstmt.setInt(1, id);
            pstmt.setInt(2, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                LinkItem linkItem = new LinkItem(rs.getInt("id_item1"),rs.getInt("id_item2"),
                        rs.getString("link_type"));
                linkItems.add(linkItem);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return linkItems;
    }
    public int findLink(int id1, int id2) {

        String SQL = "SELECT * FROM public.link " +
                "WHERE (id_item1 = ? AND id_item2 = ?) OR (id_item2 = ? AND id_item1 = ?)";
        int id=0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, id1);
            pstmt.setInt(2, id2);
            pstmt.setInt(3, id2);
            pstmt.setInt(4, id1);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                try {
                    id=rs.getInt("id_link");
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public ObservableList<HistoryItem> getBugHistoryItems(int id) {

        String SQL = "SELECT * FROM public.\"BugHistoryView\" "+"WHERE id_item = ?";
        ObservableList<HistoryItem> historyItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL))
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistoryItem historyItem = new HistoryItem(rs.getInt("id_bug"),
                        rs.getString("time"),
                        rs.getString("login"),
                        rs.getString("state"));
                historyItems.add(historyItem);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historyItems;
    }
    public ObservableList<HistoryItem> getIssueHistoryItems(int id) {

        String SQL = "SELECT * FROM public.\"IssueHistoryView\" "+"WHERE id_item = ?";
        ObservableList<HistoryItem> historyItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL))
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistoryItem historyItem = new HistoryItem(rs.getInt("id_issue"),
                        rs.getString("time"),
                        rs.getString("login"),
                        rs.getString("state"));
                historyItems.add(historyItem);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historyItems;
    }
    public ObservableList<HistoryItem> getRequirementHistoryItems(int id) {

        String SQL = "SELECT * FROM public.\"RequirementHistoryView\" "+"WHERE id_item = ?";
        ObservableList<HistoryItem> historyItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL))
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistoryItem historyItem = new HistoryItem(rs.getInt("id_requirement"),
                        rs.getString("time"),
                        rs.getString("login"),
                        rs.getString("state"));
                historyItems.add(historyItem);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historyItems;
    }

    public ObservableList<HistoryItem> getTaskHistoryItems(int id) {

        String SQL = "SELECT * FROM public.\"TaskHistoryView\" "+"WHERE id_item = ?";
        ObservableList<HistoryItem> historyItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL))
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistoryItem historyItem = new HistoryItem(rs.getInt("id_task"),
                        rs.getString("time"),
                        rs.getString("login"),
                        rs.getString("state"));
                historyItems.add(historyItem);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historyItems;
    }
    public ObservableList<HistoryItem> getTestCaseHistoryItems(int id) {

        String SQL = "SELECT * FROM public.\"TestCaseHistoryView\" "+"WHERE id_item = ?";
        ObservableList<HistoryItem> historyItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL))
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistoryItem historyItem = new HistoryItem(rs.getInt("id_case"),
                        rs.getString("time"),
                        rs.getString("login"),
                        rs.getString("state"));
                historyItems.add(historyItem);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historyItems;
    }
    public ObservableList<HistoryItem> getTestPlanHistoryItems(int id) {

        String SQL = "SELECT * FROM public.\"TestPlanHistoryView\" "+"WHERE id_item = ?";
        ObservableList<HistoryItem> historyItems = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL))
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistoryItem historyItem = new HistoryItem(rs.getInt("id_test_plan"),
                        rs.getString("time"),
                        rs.getString("login"),
                        rs.getString("state"));
                historyItems.add(historyItem);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historyItems;
    }
    public int countReqs(int month, int year){
        String SQL = "SELECT * FROM countReqs (?, ?)";
        int result = 0;
        try (Connection connection = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setInt(1,month);
            pstmt.setInt(2,year);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result = rs.getInt("countreqs");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}

