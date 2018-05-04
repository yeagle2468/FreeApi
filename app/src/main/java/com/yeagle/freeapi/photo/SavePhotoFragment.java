package com.yeagle.freeapi.photo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.yeagle.freeapi.R;

import cn.yeagle.common.fragment.BottomListDialog;
import cn.yeagle.common.utils.LogUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 */
@RuntimePermissions
public class SavePhotoFragment extends BottomListDialog {
    private static final int SAVE_ITEM = 0;

    public SavePhotoFragment() {
        setOnItemClickListener((ViewGroup container, View view, int position, Parcelable info) -> {
            switch (position) {
                case SAVE_ITEM:
//                    ChoosePhotoDialogPermissionsDispatcher.startCameraWithPermissionCheck(this);
                    SavePhotoFragmentPermissionsDispatcher.saveWithPermissionCheck(this);
                    break;
                default:
//                    ChoosePhotoDialogPermissionsDispatcher.startAlbumWithPermissionCheck(this);
                    break;
            }
        });
    }

    @Override
    protected boolean itemNeedDismiss() {
        return false;
    }

//    void share() {
//        String path = save();
//        Uri uri = UriUtil.fromFile(getContext(), new File(path));
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//        shareIntent.setType("image/*");
//        startActivity(Intent.createChooser(shareIntent, "分享到"));
//    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void save() {
        dismissAllowingStateLoss();
        // TODO save picture
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void deniedForSave() {
        LogUtils.e(TAG, "deniedForSave");
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForStorage(final PermissionRequest request) {
        request.proceed();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void neverAskForSave() {
//        Toast.makeText(this, R.string.permission_camera_neverask, Toast.LENGTH_SHORT).show();
        LogUtils.e(TAG, "showNeverAskForCamera");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SavePhotoFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//        SavePhotoFragment
    }

    public static SavePhotoFragment newInstance(Context context) {
        SavePhotoFragment f = new SavePhotoFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArray(ARGS, context.getResources().getStringArray(R.array.picture_actions));
        f.setArguments(bundle);

        return f;
    }


}
