package activity.Red;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chuanqi.yz.R;
import com.google.gson.Gson;

import java.util.HashMap;
import Constance.constance;
import Utis.Utis;
import Utis.SharePre;
import Utis.GsonUtils;
import Utis.OkHttpUtil;
import activity.BaseActivity;
import model.Result;
import model.UserMoney;
import model.YiZhuanRed;

public class SendRedActivity extends BaseActivity {
    private final  int RED_NOMRAL=1;
    private final  int RED_SUPER=2;
    private final  int YIZUANRED_PAY=3;
    private final  int YUE_PAY=4;
    private final  int WXIN_PAY=5;
    private final  int ALI_PAY=6;
    private int redType;//红包类型 100 200
    private int payType;//支付方式
    private TextView mTvYue;
    private TextView mTvYiZuan;
    private RadioButton mRb_200;
    private RadioButton mRb_100;
    private RadioButton mRbAlipay;
    private RadioButton mRbWxin;
    private RadioButton mRbYue;
    private RadioButton mRbYiZuan;
    private RelativeLayout mRtlPay;
    private int Yue;
    private double YiZhuanYue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_red);
        initview();
        initRadioButton();
        initdate();
        initevent();
    }

    /**
     * 初始化数据
     */
    private void initdate() {
        YueDate();
        YiZuanDate();
    }

    /**
     * 易赚红余额
     */
    private void YiZuanDate() {
        HashMap<String,String> maps=new HashMap<>();
        maps.put("userid", SharePre.getUserId(getApplicationContext()));
        OkHttpUtil.getInstance().Post(maps, constance.URL.YIZUAN_RED, new OkHttpUtil.FinishListener() {
            @Override
            public void Successfully(boolean IsSuccess, String data, String Msg) {
//                showTip(data.toString());
                YiZhuanRed yiZhuanRed = GsonUtils.parseJSON(data, YiZhuanRed.class);
//                YiZhuanYue=Double.parseDouble(yiZhuanRed.getYue());
                if(yiZhuanRed.getYue()==null || yiZhuanRed.getYue().equals("null")|| yiZhuanRed.getYue().equals("")){
                    mTvYiZuan.setText("余额:"+"0.0元");
                }else {
                    mTvYiZuan.setText("余额:"+yiZhuanRed.getYue()+"元");
                }
            }
        });
    }

    /**
     * 用户余额
     */
    private void YueDate() {
        HashMap<String,String> maps=new HashMap<>();
        maps.put("udid", Utis.getIMEI(getApplicationContext()));
        OkHttpUtil.getInstance().Post(maps, constance.URL.MONEY, new OkHttpUtil.FinishListener() {
            @Override
            public void Successfully(boolean IsSuccess, String data, String Msg) {
                Log.i("数据",""+data.toString());
                UserMoney userMoney = GsonUtils.parseJSON(data, UserMoney.class);
//                Yue=Integer.parseInt(userMoney.getfNotPayIncome());
                if(userMoney.getfNotPayIncome()==null || userMoney.getfNotPayIncome().equals("null")|| userMoney.getfNotPayIncome().equals("")){
                    mTvYue.setText("余额:"+"0.0元");
                }else {
                    mTvYue.setText("余额:"+userMoney.getfNotPayIncome()+"元");
                }
            }
        });
    }

    private void initview() {
        mTvYue = (TextView) findViewById(R.id.tv_yue);
        mTvYiZuan = (TextView) findViewById(R.id.tv_yizuan);
        mRb_100 = (RadioButton) findViewById(R.id.rb_100);
        mRb_200 = (RadioButton) findViewById(R.id.rb_200);
        mRbYiZuan = (RadioButton) findViewById(R.id.rb_yizuyan);
        mRbYue = (RadioButton) findViewById(R.id.rb_yue);
        mRbWxin = (RadioButton) findViewById(R.id.rb_wxin);
        mRbAlipay = (RadioButton) findViewById(R.id.rb_alipay);
        mRtlPay = (RelativeLayout) findViewById(R.id.rtl_pay);
    }


    /**
     * 初始化RadioButton
     */
    private void initRadioButton() {
        mRb_100.setChecked(true);
        mRbYiZuan.setChecked(true);
        mRbYiZuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mRbYue.setChecked(false);
                    mRbWxin.setChecked(false);
                    mRbAlipay.setChecked(false);
                }
            }
        });
        mRbYue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRbYiZuan.setChecked(false);
                    mRbWxin.setChecked(false);
                    mRbAlipay.setChecked(false);
                }
            }
        });
        mRbWxin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRbYiZuan.setChecked(false);
                    mRbYue.setChecked(false);
                    mRbAlipay.setChecked(false);
                }
            }
        });
        mRbAlipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRbYiZuan.setChecked(false);
                    mRbYue.setChecked(false);
                    mRbWxin.setChecked(false);
                }
            }
        });
        mRb_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRb_200.setChecked(false);
                }
            }
        });
        mRb_200.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRb_100.setChecked(false);
                }
            }
        });
    }
    private void initevent() {
        findViewById(R.id.rtl_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),UpdateRedActivity.class);
                startActivity(intent);
            }
        });
        mRtlPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRb_100.isChecked()){
                    redType=RED_NOMRAL;
                }else {
                    redType=RED_SUPER;
                }
                if(mRbYiZuan.isChecked()){
                    payType=YIZUANRED_PAY;
                }else if(mRbYue.isChecked()){
                    payType=YUE_PAY;
                }else if(mRbWxin.isChecked()){
                    payType=WXIN_PAY;
                } else if(mRbAlipay.isChecked()){
                    payType=ALI_PAY;
                }
                switch (redType){
                    case RED_NOMRAL://100红包
                        switch (payType){
                            case YIZUANRED_PAY://易赚支付
                                PayYizhuanRed2RedPool(100);
                                break;
                            case YUE_PAY://余额支付
                                PayYue2RedPool(100);
                                break;
                            case WXIN_PAY://微信支付
                                showTip("红包100   微信支付");
                                break;
                            case ALI_PAY://支付宝支付
                                showTip("红包100   支付宝支付");
                                break;
                        }
                        break;
                    case RED_SUPER://200红包
                        switch (payType){
                            case YIZUANRED_PAY://易赚红包支付
                                PayYizhuanRed2RedPool(500);
                                break;
                            case YUE_PAY://余额支付
                                PayYue2RedPool(500);
                                break;
                            case WXIN_PAY://微信支付
                                showTip("红包200   微信支付");
                                break;
                            case ALI_PAY://支付宝支付
                                showTip("红包200   支付宝支付");
                                break;
                        }
                        break;
                }
            }
        });
    }
    public  void  PayYue2RedPool(int price){
//        if(price<=Yue){
            startProgressDialog("正在支付中...");
            HashMap<String,String> map=new HashMap<>();
            map.put("userid", SharePre.getUserId(getApplicationContext()));
            map.put("jine",""+price);
            OkHttpUtil.getInstance().Post(map, constance.URL.PAYYUE2REDPOOL, new OkHttpUtil.FinishListener() {
                @Override
                public void Successfully(boolean IsSuccess, String data, String Msg) {
                    stopProgressDialog();
                    Result result = GsonUtils.parseJSON(data, Result.class);
                    if(result.getRun().equals("1")){
                        showTip("恭喜您，支付成功");
                        YueDate();
                    }else if(result.getRun().equals("2")){
                        showTip("抱歉，您的余额不足");
                    }else if(result.getRun().equals("0")){
                        showTip("抱歉，支付失败");
                    }else if(result.getRun().equals("3")){
                        showTip("抱歉，您今天已经充值过");
                    }else if(result.getRun().equals("4")){
                        showTip("提示:还有未抢完的红包，等抢完在发");
                    }
                }
            });
//        }else {
//            showTip("抱歉，您的余额不足");
//        }
    }
    public  void  PayYizhuanRed2RedPool(int price){
//        if(price<=YiZhuanYue){
            startProgressDialog("正在支付中...");
            HashMap<String,String> map=new HashMap<>();
            map.put("userid", SharePre.getUserId(getApplicationContext()));
            map.put("jine",""+price);
            OkHttpUtil.getInstance().Post(map, constance.URL.PAY_YIZUANRED2REDPOOL, new OkHttpUtil.FinishListener() {
                @Override
                public void Successfully(boolean IsSuccess, String data, String Msg) {
                    stopProgressDialog();
//                    showTip(data.toString());
                    Result result = GsonUtils.parseJSON(data, Result.class);
                    if(result.getRun().equals("1")){
                        showTip("恭喜您，支付成功");
                        YiZuanDate();
                    }else if(result.getRun().equals("2")){
                        showTip("抱歉，您的余额不足");
                    }else if(result.getRun().equals("0")){
                        showTip("抱歉，支付失败");
                    }else if(result.getRun().equals("3")){
                        showTip("抱歉，您今天已经充值过");
                    }else if(result.getRun().equals("4")){
                        showTip("提示:还有未抢完的红包，等抢完在发");
                    }
                }
            });
//        }else {
//            showTip("抱歉，您的余额不足");
//        }
    }
}
