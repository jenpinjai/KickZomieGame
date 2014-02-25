package sut.game01.core;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Callback;
import static playn.core.PlayN.*;

public class Circle {
  public static String IMAGE = "images/circle.png";
  private ImageLayer layer;
  private int elapsed,in=1,o;
  private final float angVel = (tick() % 10 - 5) / 1000f;
  private float t,r,k,q,tx;
  private float xx,yy,p=1,vx,ux=100,mx=3,a,b;


  public Circle(final GroupLayer cirLayer, final float x, final float y) {
    Image image = assets().getImage(IMAGE);
    layer = graphics().createImageLayer(image);
    xx=x;
    yy=y;
    a=x;
    b=y;
    k=(x/y)*10;
   
    // Add a callback for when the image loads.
    // This is necessary because we can't use the width/height (to center the
    // image) until after the image has been loaded
    image.addCallback(new Callback<Image>() {
      @Override
      public void onSuccess(Image image) {
        layer.setOrigin(image.width() / 2f, image.height() / 2f);
        layer.setTranslation(x, y);
        cirLayer.add(layer);
      }

      @Override
      public void onFailure(Throwable err) {
        log().error("Error loading image!", err);
      }
    });
  }

  public void update(int delta) {
    elapsed += delta;


    if(yy<=440.5){

          t=t+(float)0.02;
                  yy=yy+p*t+161*t*t;

           
                  
    }

    if(yy>=440.5){
      
      yy=(float)441.6;
      if(p==1)p=-((t)*161);
      else p=-((t/2)*161);
     
      if(-p>20)yy=(float)440.4;

     ux=20*t*in;
       t=0;
      in=in*-1;
    }



    if (ux>=3||ux<=-3){


          xx=xx+ux;
      
          if (xx>=600){

              ux=-ux;
              ux=ux/10;
              xx=590;
              in=in*-1;

            }



          if (xx<10){

              ux=-ux;
              ux=ux/10;
              xx=11;
             in=in*-1;

            }

       
    }







  }

  public void paint(float alpha) {

  

  
    if(yy<=440){
     k=k+in*alpha*2;
    layer.setRotation(k*(p/200));
    q=k*(p/100);
      }else if(!(-p>20)){


          if(q>0.1)q=q-(float)0.08;
           if(q<0.1)q=q+(float)0.08;
            layer.setRotation(q);

        }
    layer.setTranslation(xx,yy);


  }
  
}