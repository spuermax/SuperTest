package com.supe.supertest.common;

import com.supe.supertest.R;
import com.supe.supertest.common.utils.CommonNetworkUtils;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.exception.QsException;
import com.supermax.base.common.log.L;
import com.supermax.base.common.model.QsModel;
import com.supermax.base.common.widget.listview.LoadingFooter;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsIView;
import com.supermax.base.mvp.fragment.QsIPullFragment;
import com.supermax.base.mvp.fragment.QsIPullToRefresh;
import com.supermax.base.mvp.model.QsConstants;
import com.supermax.base.mvp.presenter.QsPresenter;

/*
 * @Author yinzh
 * @Date   2018/10/17 16:59
 * @Description
 */
public class SuperPresenter<V extends QsIView> extends QsPresenter<V>{
    @Override
    public void methodError(QsException exception) {
        L.e(initTag(), "methodError ：" + exception.getMessage());
        switch (exception.getExceptionType()) {
            case NETWORK_ERROR:
                QsToast.show(getString(R.string.network_error_msg));
            case HTTP_ERROR:
                if(!CommonNetworkUtils.isNetworkConnected()){
                    QsToast.show(getString(R.string.network_error_msg));
                }
            case UNEXPECTED:
            case CANCEL:
                resetViewState();
                break;
        }
    }



    @ThreadPoint(ThreadType.MAIN)
    private void resetViewState(){
        if (!isViewDetach()) {
            QsIView qsIview = getView();
            if (qsIview instanceof QsIPullFragment) {
                QsIPullFragment view = (QsIPullFragment) qsIview;
                view.stopRefreshing();
                view.setLoadingState(LoadingFooter.State.NetWorkError);
            } else if (qsIview instanceof QsIPullToRefresh) {
                QsIPullToRefresh view = (QsIPullToRefresh) qsIview;
                view.stopRefreshing();
                view.setLoadingState(LoadingFooter.State.NetWorkError);
            }

            if (qsIview.currentViewState() != QsConstants.VIEW_STATE_CONTENT) {
                qsIview.showErrorView();
            }
            qsIview.loadingClose();
        }
    }

    @Override
    public void paging(QsModel model) {
        super.paging(model);
    }
}
