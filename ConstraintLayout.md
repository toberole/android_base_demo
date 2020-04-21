相对定位
    layout_constraintLeft_toLeftOf	按钮1左边与按钮2左边对齐
    layout_constraintLeft_toRightOf	1左与2右对齐
    layout_constraintRight_toLeftOf	1右与2左对齐
    layout_constraintRight_toRightOf	1右与2右对齐
    layout_constraintTop_toTopOf	1顶与2顶对齐
    layout_constraintTop_toBottomOf	1顶与2底对齐
    layout_constraintBottom_toTopOf	1底与2顶对齐
    layout_constraintBottom_toBottomOf	1底与2底对齐
    layout_constraintBaseline_toBaselineOf	1基线对齐2基线
    layout_constraintStart_toEndOf	1起始与2结束对齐
    layout_constraintStart_toStartOf	1起始与2起始对齐
    layout_constraintEnd_toStartOf	1结束与2起始对齐
    layout_constraintEnd_toEndOf	1结束与2结束对齐
    layout_constraintXXX 里的 XXX 代表是这个子控件自身的哪条边( Left、Right、Top、Bottom、Baseline)，
    而 toYYYOf 里的 YYY 代表的是和约束控件的哪条边 发生约束(Left、Right、Top、Bottom、Baseline)
    
外边距约束[设置外边距时，控件外边距方向必须有app:layout_constraintXxx_约束]
    android:layout_marginStart
    android:layout_marginEnd
    android:layout_marginLeft
    android:layout_marginTop
    android:layout_marginRight
    android:layout_marginBottom
被参考控件GONE时边距才生效
    layout_goneMarginEnd
    layout_goneMarginLeft
    layout_goneMarginTop
    layout_goneMarginRight
    layout_goneMarginBottom

    被参考控件visible	            被参考控件gone
layout_margin生效	        layout_margin失效
layout_goneMargin失效	    layout_goneMargin生效



居中定位
水平	
app:layout_constraintLeft_toLeftOf="parent" 
app:layout_constraintRight_toRightOf="parent"
垂直	
app:layout_constraintTop_toTopOf="parent" 
app:layout_constraintBottom_toBottomOf="parent"

偏向
倾向 ( Bias ) 提供了两个属性用于设置偏向到的程度,类似LinnerLayout layout_weight
layout_constraintHorizontal_bias	水平(0最左边 1 最右边，默认是 0.5)
layout_constraintVertical_bias	垂直(0最上边 1 最底边，默认是 0.5)

环形定位 (Circular positioning)
layout_constraintCircle	引用另一个圆心 ID
layout_constraintCircleRadius	到其它小部件中心的距离
layout_constraintCircleAngle	小部件应处于的角度 ( 度数，从 0 到 360 ）














































