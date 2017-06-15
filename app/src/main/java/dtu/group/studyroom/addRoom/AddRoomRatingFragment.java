package dtu.group.studyroom.addRoom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dtu.group.studyroom.AddRoomActivity;
import dtu.group.studyroom.Main;
import dtu.group.studyroom.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddRoomRatingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddRoomRatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRoomRatingFragment extends Fragment {


    private View fragmentView;
    private RatingBar rateing;
    private Bundle allData;
    String mCurrentPhotoPath;

    private OnFragmentInteractionListener mListener;

    public AddRoomRatingFragment() {
        // Required empty public constructor
    }

    public static AddRoomRatingFragment newInstance() {
        AddRoomRatingFragment fragment = new AddRoomRatingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_add_room_rating, container, false);
        final Button btGoToCamera = (Button) fragmentView.findViewById(R.id.btRatingNext);
        final Button btUpload = (Button) fragmentView.findViewById(R.id.btUpload);
        rateing = (RatingBar) fragmentView.findViewById(R.id.add_room_ratingBar);
        btGoToCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCamera();
            }
        });
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        return fragmentView;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void goToCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {

          getActivity().startActivityForResult(cameraIntent,1);
        }

        Bundle data = getArguments();
        data.putDouble("rating",rateing.getRating());
        allData = data;


    }

    private void upload() {
        StudyRoom.StudyRoomFacilites facilites = new StudyRoom().new StudyRoomFacilites(
                allData.getBoolean("wifi"),
                allData.getBoolean("toilet"),
                allData.getBoolean("power"),
                allData.getBoolean("coffee"),
                allData.getBoolean("food"),
                allData.getBoolean("groups")
        );

        StudyRoom studyRoom = new StudyRoom(allData.getString("name"), allData.getString("address"), facilites, rateing.getRating());


        ((AddRoomActivity)getActivity()).saveStudyRoom(studyRoom);
    }



}
