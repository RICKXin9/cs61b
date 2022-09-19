public class NBody{
    public static double readRadius(String txt){
        In in = new In(txt);
        int number = in.readInt();
		double radius = in.readDouble();
        return radius;
    }
    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int number = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[number];
        for (int i=0;i<number;i++){
            double xxpos = in.readDouble();
            double yypos = in.readDouble();
            double xxvel = in.readDouble();
            double yyvel = in.readDouble();
            double mass = in.readDouble();
            String name = in.readString();
            Planet pla = new Planet(xxpos,yypos,xxvel,yyvel,mass,name);
            planets[i] = pla;
        }
        return planets;
    }
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);
        
        StdDraw.setScale(-1*radius,radius);
        
        StdDraw.picture(0,0,"images/starfield.jpg");
        for(Planet pla : planets){
            pla.draw();
        }
        StdDraw.enableDoubleBuffering();
        In in = new In(filename);
        int number = in.readInt();
        double[] xforces = new double[number];
        double[] yforces = new double[number];
        double t = 0.0;
        
        while(t<=T){
            for (int i=0;i<number;i++){
                double xforce = planets[i].calcNetForceExertedByX(planets);
                double yforce = planets[i].calcNetForceExertedByY(planets);
                xforces[i] = xforce;
                yforces[i] = yforce;  
            }
            for (int j=0;j<number;j++){
                planets[j].update(dt,xforces[j],yforces[j]); 
            }

            StdDraw.picture(0,0,"images/starfield.jpg");
    
            for(Planet pla : planets){
                pla.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t+=dt;
            
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }

    }
}