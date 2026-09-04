package sunrisedentalclinic;

public class SunriseDentalClinic {

    public static void main(String[] args) {

        sunrisedentalclinic.server.SunriseServer server = new sunrisedentalclinic.server.SunriseServer();
        server.startServer();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                sunrisedentalclinic.controller.AuthController auth = new sunrisedentalclinic.controller.AuthController();
                if (auth.validateLocalToken()) {
                    new sunrisedentalclinic.view.DashboardFrame().setVisible(true);
                } else {
                    new sunrisedentalclinic.view.LoginFrame().setVisible(true);
                }
            }
        });
    }

}
