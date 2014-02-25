package sut.game01.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;

/**
 * Created by JEN on 22/1/2557.
 */
public class Green {


    private Sprite sprite;
    private int spriteIndex =0;
    private boolean hasLoaded =false;
    private int nframe=3;
    private int check=0;
    private int speed=150;
    private int hp=100;
    private boolean contacted=false;
    private int contactCheck=1;
    private Body other;
    PolygonShape shape=new PolygonShape();
    public enum State{

        IDLE,RUN,PATK,DIE,BACK
    };

    private State state =State.IDLE;
    private int e=0;
    private int offset =0;
    private float x;
    private float y;
    private int e2=0;
    private Body body;
    private World world;
    public Green(final World world,final float x,final float y){
        sprite = SpriteLoader.getSprite("images/ext/green.json");
        this.x=x;
        this.y=y;
        this.world=world;

        sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                        body.applyLinearImpulse(new Vec2(100f,-8f),body.getPosition());
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


        shape.setAsBox(56*TestScreen.M_PER_PIXEL/2,sprite.layer().height()*TestScreen.M_PER_PIXEL/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 5f;
        fixtureDef.friction =5f;
        fixtureDef.restitution= 0f;
        body.createFixture(fixtureDef);
        body.setLinearDamping(0f);
        body.setTransform(new Vec2(x,y),0f);
        return body;

    }

    public void update(int delta){



        if(!hasLoaded)return;
        e+=delta;



    if(hp<=0){

        this.state=State.DIE;
        hp=0;

    }else if(this.state==State.RUN){
        body.setTransform(new Vec2(body.getPosition().x + 0.1f, body.getPosition().y), body.getAngle());
        body.applyForce(new Vec2(10f,0f),body.getPosition());
    }else if(this.state==State.BACK){
        body.setTransform(new Vec2(body.getPosition().x - 0.1f, body.getPosition().y), body.getAngle());
        body.applyForce(new Vec2(-10f,0f),body.getPosition());
    }
        if(e>speed){
            switch(state){
                case IDLE: offset =0;
                            nframe=3;
                            speed=150;
                    break;
                case RUN :
                            offset=3;
                            speed=100;
                    nframe=7;
                    break;
                case BACK :
                    offset=3;
                    speed=100;
                    nframe=7;
                    break;
                case PATK :
                    offset=13;
                    speed=70;

                    body.applyForce(new Vec2(10f,0f),body.getPosition());
                    body.setTransform(new Vec2(body.getPosition().x+0.5f,body.getPosition().y),body.getAngle());

                    if(spriteIndex>=15){

                        this.state=State.IDLE;
                    }
                    nframe=4;
                    break;
                case DIE: offset=10;
                    nframe=3;
                    speed=150;
                    break;
            }//end switch

            if(spriteIndex!=12){
                spriteIndex = offset + ((spriteIndex+1)%nframe);
            }
            sprite.setSprite(spriteIndex);
            e=0;
        }



    }


  public void paint(Clock clock){

        if(!hasLoaded)return;

      sprite.layer().setTranslation((body.getPosition().x/TestScreen.M_PER_PIXEL)-10,
                                     body.getPosition().y/TestScreen.M_PER_PIXEL);
      //sprite.layer().setRotation(body.getAngle());




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


        if(state==State.PATK){
            other.applyLinearImpulse(new Vec2(1100f,0f),other.getPosition());

        }




    }

}