public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double cons = 6.67e-11;
    public Planet(double xP, double yP, double xV,double yV, double m, String img){

        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;

    }
    public double calcDistance(Planet p){
        double deltay = Math.abs(p.yyPos - this.yyPos);
        double deltax = Math.abs(p.xxPos - xxPos);
        double distance = Math.sqrt((deltax*deltax + deltay*deltay));
        return  distance;
    }
    public double calcForceExertedBy(Planet p){
        double Force = cons*p.mass*mass/(calcDistance(p)*calcDistance(p));
        return Force;
    }
    public double calcForceExertedByX(Planet p){

        double deltax = p.xxPos-xxPos;
        double dis = calcDistance(p);
        double force = calcForceExertedBy(p);

        double forcex = force*deltax/dis;
        return forcex;


    }
    public double calcForceExertedByY(Planet p){

        double deltay = p.yyPos-yyPos;
        double dis = calcDistance(p);
        double force = calcForceExertedBy(p);

        double forcey = force*deltay/dis;
        return forcey;

    }
    public double calcNetForceExertedByX(Planet[] planets){
        double xforce = 0.0;
        for (Planet p:planets){
            if (p.equals(this)){
                continue;
            }
            xforce += calcForceExertedByX(p);

        }
        return xforce;
    }
    public double calcNetForceExertedByY(Planet[] planets){
        double yforce = 0.0;
        for (Planet p:planets){
            if (p.equals(this)){
                continue;
            }
            yforce += calcForceExertedByY(p);
        }
        return yforce;
    }
    public void update(double dt, double fX, double fY){
        double ax = fX/mass;
        double ay = fY/mass;
        double new_xxvel = xxVel+ax*dt;
        double new_yyvel = yyVel+ay*dt;
        double new_xxPos = xxPos+dt*new_xxvel;
        double new_yyPos = yyPos+dt*new_yyvel;
        xxPos = new_xxPos;
        yyPos = new_yyPos;
        xxVel = new_xxvel;
        yyVel = new_yyvel;

    }
    public void draw(){
        String file_pos = "images/" + imgFileName;
        StdDraw.picture(xxPos,yyPos,file_pos);
        
    }
}