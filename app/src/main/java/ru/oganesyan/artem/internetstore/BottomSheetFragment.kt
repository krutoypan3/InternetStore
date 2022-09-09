package ru.oganesyan.artem.internetstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment


class BottomSheetFragment : Fragment() {
    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        imgArrow = view.findViewById(R.id.imgArrow)
    }

    fun setOpenProgress(progress: Float) {
        // Rotate arrow in 1/3 of the screen to 180
        // meaning that after 1/3 we assume sheet to be opened
//        imgArrow.setRotation(Math.min(180 * progress * 3, 180f))
    }
}