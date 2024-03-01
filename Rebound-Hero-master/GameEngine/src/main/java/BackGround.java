import org.lwjgl.glfw.GLFW;
import org.sphinx.engine.GameObject;
import org.sphinx.engine.Vector2D;
import org.sphinx.engine.WindowController;
import org.sphinx.ui.*;
import org.sphinx.ui.Button;
import org.sphinx.ui.Canvas;
import org.sphinx.ui.Dialog;

import java.awt.*;



public class BackGround extends GameObject {
    Canvas canvas;
    Canvas canvas1;
    Lable lable;
    Dialog dialog;
    Button button;
    ProgressBar progressBar;

    static class TestEvent implements Runnable{
        static Dialog dialog;
        static Canvas canvas;
        static boolean sign = false;
        TestEvent(Canvas canvas,Dialog dialog){
            TestEvent.canvas = canvas;
            TestEvent.dialog = dialog;
        }
        @Override
        public void run() {
            if (!sign){
                canvas.setActive(true);
                dialog.setText("""
                       
                        %80%#30#@ffffff@"Это такой прекрасный день,%300%%100%поют птицы,%400%%110%цветут цветы. $wait$%100%
                        
                        В такие прекрасные дни,$wait$%200%такие дети, как ты%250%......
                        
                        """);
            }
            else {
                canvas.setActive(false);
            }
            sign = !sign;
        }
    }

    @Override
    public void start() {
        this.name = "BackGround";

        canvas = new Canvas(30,450, WindowController.getInstance().getWindowWidth() - 60, 330);
        canvas.setColor(new Color(0,0,0,180));

        lable = new Lable(canvas,200,40);
        lable.setText("org.sphinx.game.Player",40,Color.white);
        lable.transform.position = new Vector2D(20,10);
        dialog = new Dialog(canvas,WindowController.getInstance().getWindowWidth() - 120,280);
        dialog.transform.position = new Vector2D(30,50);
        dialog.setText("Hello World",30);
        dialog.setGameEvent(TestGameEvent.class);

        canvas1 = new Canvas(1000,20,100,100);
        button = new Button(canvas1,300,100);
        button.lable.setText("start",30,Color.black,true);
        button.setEvent(new TestEvent(canvas ,dialog));
        canvas.setActive(false);

        progressBar = new ProgressBar(100,100,400,100);
        progressBar.setDirection(ProgressBar.Direction.horizontal);
        progressBar.setColor(Color.green, Color.white);
        progressBar.setThreshold(100,1000);
    }

    @Override
    public void update() {
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_U)== GLFW.GLFW_PRESS){
            progressBar.addValue(-1);
        }
        if (GLFW.glfwGetKey(WindowController.getInstance().window, GLFW.GLFW_KEY_I)== GLFW.GLFW_PRESS){
            progressBar.addValue(1);
        }
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
