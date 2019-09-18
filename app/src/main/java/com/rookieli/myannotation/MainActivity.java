package com.rookieli.myannotation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rookieli.myannotation.shape.Circle;

public class MainActivity extends AppCompatActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Circle[] circles = new Circle[3];
		Class cls = circles.getClass();

		Log.e(getClass().getSimpleName(), "Circle[].class CanonicalName = " + cls.getCanonicalName());
		Log.e(getClass().getSimpleName(), "Circle[].class Name = " + cls.getName());
		Log.e(getClass().getSimpleName(), "Circle[].class TypeName = " + cls.getTypeName());
		Log.e(getClass().getSimpleName(), "Circle[].class SimpleName = " + cls.getSimpleName());

	}
}
