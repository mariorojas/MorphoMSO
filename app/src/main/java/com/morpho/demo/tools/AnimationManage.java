/**
 * 
 */
package com.morpho.demo.tools;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.morpho.demo.R;


/**
 * @author Alex
 *
 */
public class AnimationManage {

	public static Animation getFadeIn(){

		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new LinearInterpolator()); //add this
		fadeIn.setStartOffset(100);
		fadeIn.setDuration(1000);

		return fadeIn;
	}

	public static Animation getFadeOn(){
		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setStartOffset(100);
		fadeOut.setDuration(1000);

		return fadeOut;
	}
	
	public static Animation getFadeIn(long duration){

		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new LinearInterpolator()); //add this
		fadeIn.setStartOffset(100);
		fadeIn.setDuration(duration);

		return fadeIn;
	}
	
	public static Animation getFadeInFiexed(long duration){

		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new LinearInterpolator()); //add this
		fadeIn.setStartOffset(100);
		fadeIn.setFillAfter(true);
		fadeIn.setDuration(duration);

		return fadeIn;
	}

	public static Animation getFadeOn(long duration){
		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setStartOffset(100);
		fadeOut.setDuration(duration);

		return fadeOut;
	}
	
	public static Animation getFadeOnFixed(long duration){
		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setStartOffset(100);
		fadeOut.setFillAfter(true);
		fadeOut.setDuration(duration);

		return fadeOut;
	}

	public static Animation getRightOutAnim(Context context,long duration){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.push_right_out);
		anim.setDuration(duration);
		return anim;
	}

	public static Animation getRightInAnim(Context context,long duration){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.push_right_in);
		anim.setDuration(duration);
		
		return anim;
	}

    public static Animation getCloudAnimRight(Context context,long duration){

        Animation anim = AnimationUtils
                .loadAnimation(context, R.anim.cloud_anim_right);
        anim.setDuration(duration);

        return anim;
    }

    public static Animation getCloudAnimLeft(Context context,long duration){

        Animation anim = AnimationUtils
                .loadAnimation(context, R.anim.cloud_anim_left);
        anim.setDuration(duration);

        return anim;
    }

    public static Animation getRightInAnimBounce(Context context,long duration){

        Animation anim = AnimationUtils
                .loadAnimation(context, R.anim.push_right_in_bounce);
        anim.setDuration(duration);

        return anim;
    }
	
	public static Animation getLeftInAnim(Context context,long duration){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.push_left_in);
		anim.setDuration(duration);
		
		return anim;
	}

    public static Animation getLeftInAnimBounce(Context context,long duration){

        Animation anim = AnimationUtils
                .loadAnimation(context, R.anim.push_left_in_bounce);
        anim.setDuration(duration);

        return anim;
    }
	
	public static Animation getLeftOutAnim(Context context,long duration){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.push_left_out);
		anim.setDuration(duration);
		
		return anim;
	}
	
	public static Animation getUpAnim(Context context,long duration,boolean fixed){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.bottom_up);
		anim.setFillAfter(fixed);
		anim.setDuration(duration);
		
		return anim;
	}
	
	public static Animation getDownAnim(Context context,long duration,boolean fixed){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.bottom_down);
		anim.setFillAfter(fixed);
		anim.setDuration(duration);
		
		return anim;
	}
	
	public static Animation getUnderlineContinue(Context context){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.push_left_out);
		anim.setFillAfter(true);
		anim.setDuration(900);
		
		return anim;
	}
	
	public static Animation getFlipLeftIn(Context context){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.card_flip_left_in);
		anim.setFillAfter(true);
		anim.setDuration(500);
		
		return anim;
	}
	
	public static Animation getToMiddle(Context context,boolean fixed){

		Animation anim = AnimationUtils
				.loadAnimation(context, R.anim.to_middle);
		anim.setFillAfter(fixed);
		
		return anim;
	}


    public static void getBounceAnim(final View yourView){
        AnimatorSet set = new AnimatorSet(); //this is your animation set.
        //add as many Value animator to it as you like

        ValueAnimator scaleUp = ValueAnimator.ofFloat(1,(float)1.2);
        scaleUp.setDuration(800);
        scaleUp.setInterpolator(new BounceInterpolator()); //remove this if you prefer default interpolator
        scaleUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float newValue = (Float) valueAnimator.getAnimatedValue();
                yourView.setScaleY(newValue);
                yourView.setScaleX(newValue);
            }
        });

        ValueAnimator scaleDown = ValueAnimator.ofFloat((float)1.2,1);
        scaleUp.setDuration(800);
        scaleUp.setInterpolator(new BounceInterpolator());
        scaleUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float newValue = (Float) valueAnimator.getAnimatedValue();
                yourView.setScaleY(newValue);
                yourView.setScaleX(newValue);
            }
        });


        set.play(scaleUp);
        set.play(scaleDown).after(scaleUp);
        set.start();
    }
}

