package sut.game01.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;

/**
 * Created by JEN on 25/2/2557.
 */
public class Balloon {


    private Sprite sprite;
    private int spriteIndex =0;
    private boolean hasLoaded =false;
    private int nframe=1;
    private int check=0;
    private int speed=150;
    private int hp=100;
    private boolean contacted=false;
    private int contactCheck=1;
    private Body other;
    public enum State{

        IDLE
    };

    private State state =State.IDLE;
    private int e=0;
    private int offset =0;
    private float x;
    private float y;
    private int e2=0;
    private Body body;
    public Balloon(final World world,final float x,final float y){
        sprite = SpriteLoader.getSprite("images/ext/balloon.json");
        this.x=x;
        this.y=y;

        sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                body.applyLinearImpulse(new Vec2(100f,0f),new Vec2(15f,30f));
            }
        });

        sprite.addCallback(new Callback<Sprite>(){
            @Override
            public void onSuccess(Sprite result) {
                System.out.println(">>>>>>>>>> Loaded");
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,
                        sprite.height()/2f);
                sprite.layer().setTranslation(x,y+13f);
                body = initPhysicsBody(world,
                        TestScreen.M_PER_PIXEL*x,
                        TestScreen.M_PER_PIXEL*y);
                hasLoaded =true ;
            }
            @Override
            public void onFailure(Throwable cause) {

                PlayN.log().error("Error loading image!",cause);

            }
        });

    }



    private Body initPhysicsBody(World world,float x,float y){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body =world.createBody(bodyDef);

        PolygonShape shape=new PolygonShape();
        shape.setAsBox(56*TestScreen.M_PER_PIXEL/2,sprite.layer().height()*TestScreen.M_PER_PIXEL/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.001f;
        fixtureDef.friction =0.5f;
        fixtureDef.restitution= 0.5f;
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;

    }

    public void update(int delta){



        if(!hasLoaded)return;
        e+=delta;



        if(e>speed){
            switch(state){
                case IDLE: offset =0;
                    nframe=1;
                    speed=150;
                    break;

            }//end switch


            spriteIndex = offset + ((spriteIndex+1)%nframe);
            sprite.setSprite(spriteIndex);
            e=0;
        }



    }


    public void paint(Clock clock){

        if(!hasLoaded)return;

        sprite.layer().setTranslation((body.getPosition().x/TestScreen.M_PER_PIXEL)-10,
                body.getPosition().y/TestScreen.M_PER_PIXEL);
        sprite.layer().setRotation(body.getAngle());
        body.applyLinearImpulse(new Vec2(0f,-0.00260f),body.getPosition());



    }
    public ImageLayer layer(){

        return  this.sprite.layer();



    }

    public void setState(State state){

        this.state =state;



    }





    public void setHp(int hp){
        this.hp=hp;

    }
    public int getHp(){

        return this.hp;

    }
    public Body getBody(){

        return this.body;
    }

    public void contact(Contact contact){

        contacted =true;
        contactCheck=0;

        if(contact.getFixtureA().getBody()==body){

            other=contact.getFixtureB().getBody();
        }else{

            other=contact.getFixtureA().getBody();

        }





    }
}
