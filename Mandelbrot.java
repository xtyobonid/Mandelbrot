import java.awt.Color;
import java.io.File;

public class Mandelbrot {
    public static final int MAX_ITER = 200;
    public static final int MULTIPLIER = 5;
    public static final int PICTURE_WIDTH = 512;
    //public static int[] histogram = new int[MAX_ITER + 1];

    public static int mandelbrot(Complex c) {
        //Mandelbrot Stuff
        Complex z = new Complex(0,0);
        int n = 0;
        while (z.abs() <= 2 && n < (MAX_ITER * MULTIPLIER)) {
            z = z.times(z).plus(c);
            n ++;
        }
        return n;
    }

    public static void main(String[] args) {
        double xc = -0.75;
        double yc = 0;
        double size = 2.5;

        Picture picture = new Picture(PICTURE_WIDTH, PICTURE_WIDTH);
        for (int i = 0; i < PICTURE_WIDTH; i ++) {
            for (int j = 0; j < PICTURE_WIDTH; j ++) {
                double c = xc - size/2 + size*i/PICTURE_WIDTH;
                double d = yc - size/2 + size*j/PICTURE_WIDTH;
                Complex n = new Complex(c, d);
                
                int m = mandelbrot(n);
                
                //Histogram Coloring
                //int rgb = mandelbrot(n);
                //int red = (rgb>>16)&0xFF;
                //int green = (rgb>>8)&0xFF;
                //int blue = rgb&0xFF;
                //Color color = new Color(red, green, blue);
                
                float hue = ((float)m) / MAX_ITER;                  //Polychromatic Coloring
                int saturation = 1;                                     //
                int value = (m < (MAX_ITER * MULTIPLIER)) ? 1 : 0;       //
                int rgb = Color.HSBtoRGB(hue, saturation, value);       //
                int red = (rgb>>16)&0xFF;                               //
                int green = (rgb>>8)&0xFF;                              //
                int blue = rgb&0xFF;                                    //
                Color color = new Color(red, green, blue);              //

                
                //int gray = MAX_ITER - m;                      //Monochromatic Coloring
                //Color color = new Color(gray, gray, gray);    //
                picture.set(i, PICTURE_WIDTH-1-j, color);
            }
            System.out.println(i + " / "  + PICTURE_WIDTH);
        }
        //picture.show();
        picture.save(new File("picture2.png"));
    }

    private static class Complex {
        private double real;
        private double complex;

        public Complex(double r, double c) {
            real = r;
            complex = c;
        }

        public double getReal() {
            return real;
        }

        public double getComplex() {
            return complex;
        }

        public Complex plus(Complex b) {
            double re = this.getReal() + b.getReal();
            double im = this.getComplex() + b.getComplex();
            return new Complex(re, im);
        }

        public Complex times(Complex b) {
            double re = this.getReal() * b.getReal() - this.getComplex() * b.getComplex();
            double im = this.getReal() * b.getComplex() + this.getComplex() * b.getReal();
            return new Complex(re, im);
        }

        public double abs() {
            return Math.hypot(real, complex);
        }
    }
	
	
}