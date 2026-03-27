import controller.DatabaseHelper;
import controller.StaffController;
import controller.StaffDAO;
import view.StaffView;

public class Main {
    public static void main(String[] args) {
        // 1. Connect to DB and create table if not exists
        DatabaseHelper.initializeTable();

        // 2. Wire up MVC
        StaffView       view       = new StaffView();
        StaffDAO        dao        = new StaffDAO();
        StaffController controller = new StaffController(view, dao);

        // 3. Start the app
        controller.run();
    }
}