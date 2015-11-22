package com.srx.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.funitlab.jobhack.R;

public class TabMainView extends View {
	private RectF rectF = new RectF();
	private Paint paint;
	private PaintFlagsDrawFilter paintFlagsDrawFilter;
	private DecelerateInterpolator decelerateInterpolator;
	private OvershootInterpolator overshootInterpolator;

	/**
	 * å›¾æ ‡é›†å�ˆ
	 */
	private Bitmap centerBtnsBitmap[] = new Bitmap[4];

	private Bitmap mainBtnBitmap;

	private Bitmap leftBtnBitmap;

	private Bitmap rightBtnBitmap;

	/**
	 * å›¾æ ‡çš„ä¸­ç‚¹é›†å�ˆ
	 */
	private float centerXs[] = new float[5];

	private float leftBtnCenterX;

	private float rightBtnCenterX;

	/**
	 * æœ€å¤§çš„åœ†å�Šå¾„
	 */
	private float maxOvalR = 0;
	/**
	 * æœ€å°�çš„åœ†å�Šå¾„
	 */
	private float minOvalR = 0;
	/**
	 * åœ†ç‚¹çš„å�Šå¾„
	 */
	private float dotR = 0;
	/**
	 * åœ†ç‚¹çš„åœ†å¿ƒ å��æ ‡
	 */
	private float dotY = 0;
	/**
	 * æœ€å¤§çš„åœ†ç§»åŠ¨é•¿åº¦
	 */
	private float maxMoveLength = 0;
	/**
	 * å·¦è¾¹çŸ©å½¢çš„åœ†å¿ƒå��æ ‡
	 */
	private float leftCenterX = 0;
	/**
	 * å�³è¾¹çŸ©å½¢çš„åœ†å¿ƒå��æ ‡
	 */
	private float rightCenterX = 0;
	/**
	 * å·¦è¾¹æŒ‰é’®çš„åœ†å¿ƒå��æ ‡æœ€å¤§å€¼
	 */
	private float secondlyFirstX = 0;

	/**
	 * æ—‹è½¬è§’åº¦é›†å�ˆï¼š0ã€�ä¸­å¿ƒæŒ‰é’®è§’åº¦ 1ã€�ä¸»æŒ‰é’®çš„è§’åº¦
	 * 2ã€�å·¦è¾¹æŒ‰é’®è§’åº¦ 3ã€�å�³è¾¹æŒ‰é’®è§’åº¦
	 */
	private float mainBtnDegrees;

	private float leftBtnDegrees;

	private float rightBtnDegrees;

	private float centerBtnsDegrees;

	/**
	 * åŠ¨ç”»æ—¶é—´é•¿åº¦
	 */
	private long durationMillis = 300;

	/**
	 * æ˜¯å�¦å·²ç»�å±•å¼€
	 */
	private boolean isOpened = true;
	/**
	 * æ˜¯å�¦ç”»ä¸»å›¾æ ‡
	 */
	private boolean drawIcons = false;
	/**
	 * æ˜¯å�¦ç”»åœ†ç‚¹
	 */
	private boolean drawDot = false;

	/**
	 * ç‚¹å‡»çš„ä½�ç½®
	 */
	private int position = 0;
	/**
	 * å½“å‰�æŒ‰ä¸‹çš„ä½�ç½®
	 */
	private int sizePosition = -1;
	/**
	 * æŽ§ä»¶å®½åº¦
	 */
	private int width = 0;
	/**
	 * æŽ§ä»¶é«˜åº¦
	 */
	private int height = 0;
	/**
	 * æŽ§ä»¶çš„æ¨ªå��æ ‡ä¸­ç‚¹
	 */
	private int centerX = 0;
	/**
	 * æŽ§ä»¶çš„çºµå��æ ‡ä¸­ç‚¹
	 */
	private int centerY = 0;
	/**
	 * æŽ§ä»¶é«˜åº¦çš„DPå€¼
	 */
	private int heightDip = 66;

	/**
	 * é¢œè‰²é›†å�ˆ
	 */
	private int colors[] = new int[3];

	/**
	 * åŠ¨ç”»ç›‘å�¬å™¨
	 */
	private OnAnimationEndListener animationEndListener;

	public void setMenuBitmaps(int position, int resId) {
		centerBtnsBitmap[position] = resId == 0 ? null : BitmapFactory
				.decodeResource(getResources(), resId);
		invalidate();
	}

	public void setRightBtnBitmap(int resId) {
		this.rightBtnBitmap = resId == 0 ? null : BitmapFactory.decodeResource(
				getResources(), resId);
		invalidate();
	}

	public void setLeftBtnBitmap(int resId) {
		this.leftBtnBitmap = resId == 0 ? null : BitmapFactory.decodeResource(
				getResources(), resId);
		invalidate();
	}

	public void setMainBtnBitmap(int resId) {
		this.mainBtnBitmap = resId == 0 ? null : BitmapFactory.decodeResource(
				getResources(), resId);
		invalidate();
	}

	private Animation animation = new Animation() {

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			if (isOpened) {
				leftCenterX = centerX
						- maxMoveLength
						* (1 - decelerateInterpolator
								.getInterpolation(interpolatedTime));
				rightCenterX = width - leftCenterX;
				leftBtnCenterX = secondlyFirstX
						- (secondlyFirstX * 3)
						* (1 - overshootInterpolator
								.getInterpolation(interpolatedTime));
				rightBtnCenterX = width - leftBtnCenterX;
				leftBtnDegrees = overshootInterpolator
						.getInterpolation(interpolatedTime) * 360;
				rightBtnDegrees = 360 - leftBtnDegrees;
				mainBtnDegrees = rightBtnDegrees;
			} else {
				leftCenterX = centerX
						- maxMoveLength
						* decelerateInterpolator
								.getInterpolation(interpolatedTime);
				rightCenterX = width - leftCenterX;
				leftBtnCenterX = secondlyFirstX
						- (secondlyFirstX * 3)
						* overshootInterpolator
								.getInterpolation(interpolatedTime);
				rightBtnCenterX = width - leftBtnCenterX;
				leftBtnDegrees = (1 - overshootInterpolator
						.getInterpolation(interpolatedTime)) * 360;
				rightBtnDegrees = 360 - leftBtnDegrees;
				mainBtnDegrees = rightBtnDegrees;
			}
			invalidate();
		}
	};

	private Animation rotateAnimation = new Animation() {
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			centerBtnsDegrees = overshootInterpolator
					.getInterpolation(interpolatedTime) * 360;
			invalidate();
		}
	};

	public long getDuration() {
		return durationMillis;
	}

	private AnimationListener animListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			drawDot = false;
			drawIcons = false;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (!isOpened) {
				clearAnimation();
				startAnimation(rotateAnimation);
			} else if (animationEndListener != null) {
				animationEndListener.onAnimationEnd();
			}
		}
	};

	private AnimationListener rotateAnimListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			drawIcons = true;
			drawDot = true;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (animationEndListener != null)
				animationEndListener.onAnimationEnd();
		}
	};

	public TabMainView(Context context) {
		this(context, null);
	}

	public TabMainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
		initLength();
		initAnimation();
	}

	private void initPaint() {
		colors[0] = getResources().getColor(R.color.guillotine_background);
		colors[1] = getResources().getColor(R.color.guillotine_background);
		colors[2] = getResources().getColor(R.color.guillotine_background);
		paint = new Paint();
		paint.setColor(colors[0]);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paintFlagsDrawFilter = new PaintFlagsDrawFilter(0,
				Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
	}

	private void initLength() {
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = (int) (dm.density * heightDip + 0.5f);
		centerX = width / 2;
		centerY = height / 2;
		maxOvalR = height / 2;
		minOvalR = maxOvalR * 2 / 3;
		leftCenterX = width / 2;
		rightCenterX = leftCenterX;
		leftBtnCenterX = minOvalR + maxOvalR / 2;
		rightBtnCenterX = width - leftBtnCenterX;
		secondlyFirstX = leftBtnCenterX;
		centerXs[0] = maxOvalR * 3 / 2;
		centerXs[2] = centerX;
		maxMoveLength = width / 2 - centerXs[0];
		centerXs[1] = (centerX - centerXs[0]) / 2 + centerXs[0];
		centerXs[3] = width - centerXs[1];
		centerXs[4] = width - centerXs[0];
		dotR = dm.density * 2 + 0.5f;
		dotY = height - maxOvalR / 3 * 2 + 4 * dotR;
		setLayoutParams(new RelativeLayout.LayoutParams(width, height));
	}

	private void initAnimation() {
		decelerateInterpolator = new DecelerateInterpolator(2f);
		overshootInterpolator = new OvershootInterpolator(1.0f);
		animation.setDuration(durationMillis);
		animation.setAnimationListener(animListener);
		rotateAnimation.setDuration(durationMillis);
		rotateAnimation.setAnimationListener(rotateAnimListener);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.setDrawFilter(paintFlagsDrawFilter);
		// //////back///////
		paint.setColor(colors[0]);
		rectF.set(leftCenterX - maxOvalR, centerY - maxOvalR, leftCenterX
				+ maxOvalR, centerY + maxOvalR);
		canvas.drawArc(rectF, 90, 180, false, paint);
		rectF.set(rightCenterX - maxOvalR, centerY - maxOvalR, rightCenterX
				+ maxOvalR, centerY + maxOvalR);
		canvas.drawArc(rectF, -90, 180, false, paint);
		if (leftCenterX != rightCenterX)
			canvas.drawRect(leftCenterX - 0.5f, 0, rightCenterX + 0.5f,
					maxOvalR * 2, paint);

		// //////Dot////////
		if (drawDot) {
			paint.setColor(colors[2]);
			float centerX = centerXs[position];
			rectF.set(centerX - dotR, dotY - dotR, centerX + dotR, dotY + dotR);
			canvas.drawArc(rectF, 0, 360, false, paint);
		}
		// //////Icons//////////

		// ç»˜åˆ¶ä¸­å¿ƒæŒ‰é’®
		if (mainBtnBitmap != null) {
			drawIcon(mainBtnBitmap, canvas, maxOvalR, centerXs[2],
					mainBtnDegrees, 2);
		}

		// ç»˜åˆ¶è�œå�•æŒ‰é’®
		for (int i = 0; i < centerBtnsBitmap.length; i++) {
			if (centerBtnsBitmap[i] != null) {
				int btn = i;
				if (i > 1)
					btn += 1;
				if (drawIcons)
					drawIcon(centerBtnsBitmap[i], canvas, maxOvalR,
							centerXs[btn], centerBtnsDegrees, btn);
			}
		}

		// ç»˜åˆ¶å·¦ä¾§æŒ‰é’®
		if (leftBtnBitmap != null) {
			rectF.set(leftBtnCenterX - minOvalR, centerY - minOvalR,
					leftBtnCenterX + minOvalR, centerY + minOvalR);
			canvas.drawArc(rectF, 0, 360, false, paint);
			drawIcon(leftBtnBitmap, canvas, maxOvalR, leftBtnCenterX,
					leftBtnDegrees, 10);
		}

		// ç»˜åˆ¶å�³ä¾§æŒ‰é’®
		if (rightBtnBitmap != null) {
			rectF.set(rightBtnCenterX - minOvalR, centerY - minOvalR,
					rightBtnCenterX + minOvalR, centerY + minOvalR);
			canvas.drawArc(rectF, 0, 360, false, paint);
			drawIcon(rightBtnBitmap, canvas, maxOvalR, rightBtnCenterX,
					rightBtnDegrees, 11);
		}
		super.onDraw(canvas);
	}

	private void drawIcon(Bitmap bitmap, Canvas canvas, float radius,
			float pointX, float degrees, int position) {
		canvas.save();
		if (position == sizePosition)
			canvas.scale(0.8f, 0.8f, pointX, centerY);
		canvas.rotate(degrees, pointX, centerY);
		rectF.set(pointX - radius / 3, centerY - radius / 3, pointX + radius
				/ 3, centerY + radius / 3);
		canvas.drawBitmap(bitmap, null, rectF, paint);
		canvas.restore();
	}

	public void onClick(int position) {
		if (position < 10) {
			if (position != 2)
				this.position = position;
			isOpened = !isOpened;
			startAnimation(animation);
		}
	}

	public void initPosition(int position) {
		if (position < 10 && position != 2)
			this.position = position;
	}

	public void onDown(int position) {
		sizePosition = position;
		invalidate();
	}

	public void onUp(int position) {
		sizePosition = -1;
		invalidate();
	}

	public void setOnAnimationEndListener(
			OnAnimationEndListener animationEndListener) {
		this.animationEndListener = animationEndListener;
	}

	public interface OnAnimationEndListener {
		void onAnimationEnd();
	}
}
