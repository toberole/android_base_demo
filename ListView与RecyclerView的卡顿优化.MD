在ListView和RecyclerView被加载出来显示之后， 
再滑动会出现卡顿，而后就顺畅多了，问题是显示之后，在滑动为啥会卡顿呢 ？？
预处理加载item:
ListView的处理办法： 
Method method = ShareReflectUtil.findMethod(this,"addViewBelow",View.class, int.class);
int position = getLastVisiblePosition();
View view = getChildAt(position);
for(inti =0;i < size;i++) {
    method.invoke(this,view,i + position);
}

RecyclerView的处理办法： 
RecyclerView.RecycledViewPool pool = recyclerView.getRecycledViewPool();
pool.setMaxRecycledViews(viewType,count);
for(intindex =0;index < count;index++) {
    pool.putRecycledView(recyclerView.getAdapter().createViewHolder(recyclerView,0));
}