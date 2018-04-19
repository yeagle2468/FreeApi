package cn.yeagle.common.mvp;

/**
 * 跟 IPreseneter 一样都不用使用泛型，免得冲突
 */
public interface IView {
    void showLoading();
    void hideLoading();
}
