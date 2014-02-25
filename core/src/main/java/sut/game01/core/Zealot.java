package sut.game01.core;

import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;

/**
 * Created by JEN on 21/1/2557.
 */
public class Zealot  {

    private Sprite sprite;
    private int spriteIndex =0;
    private boolean hasLoaded =false;

    public enum State{

        IDLE,RUN,ATTK
    };


    private State state =State.IDLE;
    private int e=0;
    private int offset =0;


    public Zealot(final float x,final float y){
        sprite = SpriteLoader.getSprite("images/ext/zealot.json");
        sprite.addCallback(new Callback<Sprite>(){

            @Override
            public void onSuccess(Sprite result) {
                System.out.println(">>>>>>>>>> Loaded");
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,
                        sprite.height()/2f);

                sprite.layer().setTranslation(x,y+13f);
                hasLoaded =true ;

            }

            @Override
            public void onFailure(Throwable cause) {

                PlayN.log().error("Error loading image!",cause);

            }
        });


    sprite.layer().addListener(new Pointer.Adapter(){

        @Override
        public void onPointerEnd(Pointer.Event event) {
            state = State.ATTK;
            spriteIndex =-1;
            e=0;
        }
    });


    }


    public void update(int delta){
        if(!hasLoaded)return;
        e+=delta;

        if(e>150){
            switch(state){
                case IDLE: offset =0;
                        break;

                case RUN : offset=4;
                        break;

                case ATTK: offset=8;
                        if(spriteIndex == 10){

                            state = State.IDLE;
                        }
                break;

            }
            spriteIndex = offset + ((spriteIndex+1)%4);
            sprite.setSprite(spriteIndex);
            e=0;


        }



    }

    public ImageLayer layer(){

                return  this.sprite.layer();



    }








}
