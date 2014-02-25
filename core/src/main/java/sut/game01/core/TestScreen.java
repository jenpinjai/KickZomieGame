package sut.game01.core;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import pythagoras.f.IRectangle;
import pythagoras.f.Rectangle;
import react.UnitSlot;



import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;



import javax.lang.model.element.*;
import java.util.ArrayList;
import java.util.List;

import static  playn.core.PlayN.*;

/**
 * Created by JEN on 21/1/2557.
 */
public class TestScreen extends UIScreen {


    public static float M_PER_PIXEL =1/26.666667f;
    //size of world

    private static int width =24; //640px in meter
    private static  int height =18;//480px  in meter

    private World world,world2;

    private DebugDrawBox2D debugDraw;


    private final ScreenStack ss;

    GroupLayer cirLayer;
    List<Circle> circle = new ArrayList<Circle>(0);

    private Zealot z;
    private Green green,g2;
    private Balloon ball;
    private Root root;
    private Label hp;
    private boolean showDebugDraw=true;



    public TestScreen (ScreenStack ss){

        this.ss = ss;

    }


    @Override
    public void wasAdded() {
        super.wasAdded();

    Vec2 gravity =new Vec2(0.0f,10.0f);
        world=new World(gravity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if(contact.getFixtureA().getBody()==green.getBody()||contact.getFixtureB().getBody()==green.getBody()){

                                    green.contact(contact);
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });



        Vec2 gravity2 =new Vec2(0.0f,0.0f);
        world2=new World(gravity2,true);
        world2.setWarmStarting(true);
        world2.setAutoClearForces(true);


        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(0f,height-2),new Vec2(width,height-2));
        ground.createFixture(groundShape,0.0f);

        Body wall1 = world.createBody(new BodyDef());
        PolygonShape wall1Shape = new PolygonShape();
        wall1Shape.setAsEdge(new Vec2(0f,height-2),new Vec2(0,0));
        wall1.createFixture(wall1Shape,0.0f);


        Body wall2 = world.createBody(new BodyDef());
        PolygonShape wall2Shape = new PolygonShape();
        wall2Shape.setAsEdge(new Vec2(width,height-2),new Vec2(width,0));
        wall2.createFixture(wall2Shape,0.0f);

        //////////////////////////////////////////////////////////////////

        Body ground2 = world2.createBody(new BodyDef());
        PolygonShape groundShape2 = new PolygonShape();
        groundShape2.setAsEdge(new Vec2(0f,height-2),new Vec2(width,height-2));
        ground2.createFixture(groundShape2,0.0f);

        Body wall12 = world2.createBody(new BodyDef());
        PolygonShape wall1Shape2 = new PolygonShape();
        wall1Shape2.setAsEdge(new Vec2(0f,height-2),new Vec2(0,0));
        wall12.createFixture(wall1Shape2,0.0f);


        Body wall22 = world2.createBody(new BodyDef());
        PolygonShape wall2Shape2 = new PolygonShape();
        wall2Shape2.setAsEdge(new Vec2(width,height-2),new Vec2(width,0));
        wall22.createFixture(wall2Shape2,0.0f);

        Body wall32 = world.createBody(new BodyDef());
        PolygonShape wall3Shape2 = new PolygonShape();
        wall3Shape2.setAsEdge(new Vec2(0,0),new Vec2(width,0-2));
        wall32.createFixture(wall3Shape2,0.0f);



        Image bgImage = assets().getImage("images/bg.png");
        bgImage.addCallback(new Callback<Image>(){


            @Override
            public void onSuccess(Image result) {

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });


        Image backImage = assets().getImage("images/backbtt.png");
        backImage.addCallback(new Callback<Image>(){


            @Override
            public void onSuccess(Image result) {

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });

        ImageLayer bgLayer =graphics().createImageLayer(bgImage);
        layer.add(bgLayer);

        ImageLayer backLayer =graphics().createImageLayer(backImage);
        layer.add(backLayer);
        backLayer.setTranslation(520,0);

        if(showDebugDraw){

            CanvasImage image = graphics().createImage((int) (width / TestScreen.M_PER_PIXEL), (int) (height / TestScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit|DebugDraw.e_jointBit|DebugDraw.e_aabbBit);
            debugDraw.setCamera(0,0,1f/TestScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
            world2.setDebugDraw(debugDraw);

        }


        backLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerStart(Pointer.Event event) {
                Image backImage = assets().getImage("images/backbtt2.png");
                backImage.addCallback(new Callback<Image>(){

                    @Override
                    public void onSuccess(Image result) {
                    }
                    @Override
                    public void onFailure(Throwable cause) {
                    }
                });

                ImageLayer backLayer =graphics().createImageLayer(backImage);
                layer.add(backLayer);
                backLayer.setTranslation(520,0);
            }
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(ss.top());
            }
        });



        z= new Zealot(500f,300f);
        layer.add(z.layer());

        green= new Green(world,100f,300f);

        layer.add(green.layer());
        g2=new Green(world,450f,300f);
        layer.add(g2.layer());

        ball =new Balloon(world,150f,200f);
        layer.add(ball.layer());



        SurfaceImage s = graphics().createSurface(640f, 480f);

        s.surface().fillRect(100f,100f,50f,50f);
        s.surface().setFillColor(10);

        ImageLayer square =graphics().createImageLayer(s);
        layer.add(square);

        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), layer);

        root.setSize(width(), height());

        hp= new Label(String.valueOf(green.getHp()))
                .addStyles(Style.FONT.is(HomeScreen.TITLE_FONT));

        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if (event.key() == Key.D) {
                    green.setState(Green.State.RUN);
                }
                if (event.key() == Key.A) {
                    green.setState(Green.State.BACK);
                }
                if (event.key()== Key.J){
                    green.setState(Green.State.PATK);

                }
            }

            @Override
            public void onKeyUp(Keyboard.Event event) {
                if (event.key() == Key.ESCAPE) {
                    ss.remove(ss.top());
                }
                if (event.key() == Key.D) {
                    green.setState(Green.State.IDLE);
                }
                if (event.key() == Key.A) {
                    green.setState(Green.State.IDLE);
                }

            }


        });



//        cirLayer = graphics().createGroupLayer();
//        layer.add(cirLayer);
//
//        assets().getImage(Circle.IMAGE);
//
//        pointer().setListener(new Pointer.Adapter() {
//            @Override
//            public void onPointerEnd(Pointer.Event event) {
//                Circle cir = new Circle(cirLayer, event.x(), event.y());
//                circle.add(cir);
//            }
//        });


    }




    @Override
    public void update(int delta) {


        world.step(0.033f,10,10);
        world2.step(0.033f,10,10);
        root.destroyAll();

        hp= new Label(String.valueOf(green.getHp())).addStyles(Style.FONT.is(HomeScreen.TITLE_FONT));
        root.add(hp);


           g2.update(delta);
          z.update(delta);
        ball.update(delta);


          green.update(delta);

//        for (Circle cir : circle) {
//            cir.update(delta);
//        }
//
//        for (Circle cir : circle) {
//            cir.paint(delta);
//        }



    }
    public void setState(Green.State state){

        green.setState(state);

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);

        green.paint(clock);
        g2.paint(clock);
        ball.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            world2.drawDebugData();
        }
    }
}
