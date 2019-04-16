package uz.codic.ahmadtea.data.network.model;

public class Request {

    String routingKey;
    Params params;

    public Request()
    {

    }
    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }


    public static class Params{
        String login;
        public Params(){
        }

        public Params(String login){

        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

}

