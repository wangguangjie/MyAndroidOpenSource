package com.org.wangguangjie.application2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.org.wangguangjie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangguangjie on 16/9/11.
 */
public class Application2Start extends Activity {

    private List<Button> bts_operator=new ArrayList<>();
    private List<Button> bts_number=new ArrayList<>();
    private List<Button> bts_fc=new ArrayList<>();
    private Button bt_dot;
    private Button bt_clear;
    private Button bt_delete;
    //display;
    private EditText edt_diplay;

    private double value;
    private Boolean firstinput;
    private String bf_operator;
    private Boolean firstcalulate;

    private Boolean isNumber;

    //private
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.application2_start);
        //
        initData();
        initView();
    }
    //init Data;
    public void initData()
    {
        value=0;
        firstinput=true;
        firstcalulate=true;
        bf_operator="";
        isNumber=true;
        bf_operator="";
    }
    //init View;
    public void initView()
    {
        //clear,delete,dot;
        bt_clear=(Button)findViewById(R.id.bt_clear);
        bt_delete=(Button)findViewById(R.id.bt_del);
        bt_dot=(Button)findViewById(R.id.bt_dot);
        edt_diplay=(EditText)findViewById(R.id.display);
        disPlay("0.0");
        //number;
        bts_number.add(0,(Button)findViewById(R.id.bt_0));
        bts_number.add(1,(Button)findViewById(R.id.bt_1));
        bts_number.add(2,(Button)findViewById(R.id.bt_2));
        bts_number.add(3,(Button)findViewById(R.id.bt_3));
        bts_number.add(4,(Button)findViewById(R.id.bt_4));
        bts_number.add(5,(Button)findViewById(R.id.bt_5));
        bts_number.add(6,(Button)findViewById(R.id.bt_6));
        bts_number.add(7,(Button)findViewById(R.id.bt_7));
        bts_number.add(8,(Button)findViewById(R.id.bt_8));
        bts_number.add(9,(Button)findViewById(R.id.bt_9));
        //operator
        bts_operator.add(0,(Button)findViewById(R.id.bt_plus));
        bts_operator.add(1,(Button)findViewById(R.id.bt_sub));
        bts_operator.add(2,(Button)findViewById(R.id.bt_mul));
        bts_operator.add(3,(Button)findViewById(R.id.bt_div));
        bts_operator.add(4,(Button)findViewById(R.id.bt_equ));
        //function;
        bts_fc.add(0,(Button)findViewById(R.id.bt_sin));
        bts_fc.add(1,(Button)findViewById(R.id.bt_tan));
        bts_fc.add(2,(Button)findViewById(R.id.bt_cos));
        bts_fc.add(3,(Button)findViewById(R.id.bt_log));
        bts_fc.add(4,(Button)findViewById(R.id.bt_n));
        //add Listener;
        initCLickListener();
    }
    private void initCLickListener()
    {
        //clear
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=0.0;
                firstinput=true;
                firstcalulate=true;
                bf_operator="";
                //edt_diplay.setText(express);
                disPlay(Double.toString(value));
            }
        });
        //delete;
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String express=edt_diplay.getText().toString();
                if(express.length()>0) {
                    if (!express.equals("0.0")) {
                        express = express.substring(0, express.length() - 1);
                        disPlay(express);
                    }
                }
            }
        });
        //dot
        bt_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String express=edt_diplay.getText().toString();
                if(express.contains("."))
                {
                    hint();
                }
                else if(firstinput)
                {
                    hint();
                }
                else
                {
                    express=express+".";
                    disPlay(express);
                }
            }
        });
        //number;
        NumberLister numberLister=new NumberLister();
        for(int i=0;i<bts_number.size();i++)
        {
            bts_number.get(i).setOnClickListener(numberLister);
        }
        //operator;
        OpertorListener opertorListener=new OpertorListener();
        for(int i=0;i<bts_operator.size();i++)
        {
            bts_operator.get(i).setOnClickListener(opertorListener);
        }
        //function;
        FunctionListener functionListener=new FunctionListener();
        for(int i=0;i<bts_fc.size();i++)
        {
            bts_fc.get(i).setOnClickListener(functionListener);
        }
    }

    //listener;
    class OpertorListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Button b=(Button)v;
            String s=b.getText().toString();

            if(bf_operator.equals("="))isNumber=true;
            if(isNumber) {
                if (firstcalulate) {
                    bf_operator = s;
                    try {
                        value = Double.parseDouble(edt_diplay.getText().toString());
                        disPlay(s);
                        firstcalulate = false;
                    } catch (Exception e) {
                        hint();
                    }
                } else {
                    //bf_operator=s;
                    caculate(bf_operator);
                   // disPlay(s);
                    bf_operator = s;
                }
                if (s.equals("=")) {
                    disPlay(Double.toString(value));
                    firstcalulate = true;
                }
                else
                {
                    disPlay(s);
                }
                firstinput = true;
                isNumber=false;
            }
            else
            {
                hint();
            }
        }
    }
    class NumberLister implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Button bt=(Button)v;
            String s=bt.getText().toString();
            if(firstinput)
            {
                disPlay(s);
                firstinput=false;
            }
            else
            {
                disPlay(edt_diplay.getText().toString()+s);
            }
            isNumber=true;
        }
    }
    class FunctionListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Button bt=(Button)v;
            String s=bt.getText().toString();
            try
            {
                Double d=Double.parseDouble(edt_diplay.getText().toString());
                Double d1=1.0;
                switch (s)
                {
                    case "sin":
                        d1=Math.sin(d);
                        break;
                    case "cos":
                        d1=Math.cos(d);
                        break;
                    case "tan":
                        d1=Math.tan(d);
                        break;
                    case "n!":
                    {
                        if(d>=0)
                        {
                            for(int i=1;i<=d;i++)
                                d1*=i;
                        }
                        else
                        {
                            hint();
                        }
                            break;
                    }
                    case "log":
                    {
                        if(d>0)
                        {
                            d1=Math.log(d);
                        }
                        else
                        {
                            hint();
                        }
                    }
                        break;
                    default:break;
                }
                disPlay(d1.toString());
            }
            catch (Exception e)
            {
                hint();
            }

        }
    }

    //input express;
    private void disPlay(String s)
    {
        edt_diplay.setText(s);
        //移动光标到末尾;
        edt_diplay.setSelection(s.length());
    }
    //hint
    private void hint()
    {
        new AlertDialog.Builder(Application2Start.this).setTitle("提示").setMessage("表达式输入错误！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // return ;
            }
        }).create().show();
    }
    private void caculate(String operator)
    {
        switch (operator)
        {
            case "+":
                value=value+Double.parseDouble(edt_diplay.getText().toString());
                break;
            case "-":
                value=value-Double.parseDouble(edt_diplay.getText().toString());
                break;
            case "*":
                value=value*Double.parseDouble(edt_diplay.getText().toString());
                break;
            case "/":
                value=value/Double.parseDouble(edt_diplay.getText().toString());
                break;
            case "=":
                  break;
            default:break;
        }
    }

}
