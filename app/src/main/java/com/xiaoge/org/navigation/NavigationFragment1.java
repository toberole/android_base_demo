package com.xiaoge.org.navigation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xiaoge.org.R;
import com.xiaoge.org.util.LogUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class NavigationFragment1 extends Fragment implements View.OnClickListener {
    public static final String TAG = NavigationFragment1.class.getSimpleName();

    private Activity activity;

    public NavigationFragment1() {
        super();
        LogUtil.i(TAG, TAG + "#NavigationFragment1");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        LogUtil.i(TAG, TAG + "#onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int test = 0;
        if (null != savedInstanceState) {
            test = savedInstanceState.getInt("test");
        }
        LogUtil.i(TAG, TAG + "#onCreate test: " + test);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, TAG + "#onCreateView");

        View v = inflater.inflate(R.layout.navigationfragment_layout, container, false);
        Button btn1 = v.findViewById(R.id.btn1);
        Button btn2 = v.findViewById(R.id.btn2);
        btn1.setText(this.getClass().getSimpleName() + "_btn1");
        btn2.setText(this.getClass().getSimpleName() + "_btn2");
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int test = 0;
        if (null != savedInstanceState) {
            test = savedInstanceState.getInt("test");
        }
        LogUtil.i(TAG, TAG + "#onViewCreated test: " + test);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.i(TAG, TAG + "#onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i(TAG, TAG + "#onDestroyView");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                // 传递参数
                NavigationFragment1Args.Builder builder = new NavigationFragment1Args.Builder(3000);// test1 没有默认值 在此需要传递值
                builder.setTest(2000);
                NavigationFragment1Args navigationFragment1Args = builder.build();
                Navigation.findNavController(v).navigate(R.id.action_page2, navigationFragment1Args.toBundle());
                break;
            case R.id.btn2:
                break;
            default:
                break;
        }
    }
    /*
        启用 Safe Args 后，生成的代码会为每个操作包含以下类型安全的类和方法，以及每个发送和接收目的地。

        为生成操作的每一个目的地创建一个类。该类的名称是在源目的地的名称后面加上“Directions”。例如，如果源目的地是名为 SpecifyAmountFragment 的 Fragment，则生成的类的名称为 SpecifyAmountFragmentDirections。

        该类会为源目的地中定义的每个操作提供一个方法。

        对于用于传递参数的每个操作，都会创建一个 inner 类，该类的名称根据操作的名称确定。例如，如果操作名称为 confirmationAction,，则类名称为 ConfirmationAction。如果您的操作包含不带 defaultValue 的参数，则您可以使用关联的 action 类来设置参数值。

        为接收目的地创建一个类。该类的名称是在目的地的名称后面加上“Args”。例如，如果目的地 Fragment 的名称为 ConfirmationFragment,，则生成的类的名称为 ConfirmationFragmentArgs。可以使用该类的 fromBundle() 方法检索参数。
    */

}
