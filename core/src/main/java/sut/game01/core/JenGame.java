package sut.game01.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.util.List;

import playn.core.*;

import java.lang.*;

import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

public class JenGame extends Game.Default {

  public static final int UPDATE_RATE = 17;
  private ScreenStack ss=new ScreenStack();
  protected final Clock.Source clock =new Clock.Source(UPDATE_RATE);
    final  HomeScreen home=  new HomeScreen(ss);



  public JenGame() {
    super(UPDATE_RATE); // call update every 33ms (30 times per second)
  }



  @Override
  public void init() {


    ss.push(home);



     
 
  }

    public  HomeScreen getHomeScreen(){

                    return home;

    }

    @Override
  public void update(int delta) {

    ss.update(delta);

  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
 

    clock.paint(alpha);
    ss.paint(clock);

   
   
  }
}
