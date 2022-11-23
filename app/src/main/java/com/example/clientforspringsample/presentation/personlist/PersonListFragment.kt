package com.example.clientforspringsample.presentation.personlist

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.clientforspringsample.R
import com.example.clientforspringsample.databinding.EditPersonDialogBinding
import com.example.clientforspringsample.databinding.FragmentPersonListBinding
import com.example.clientforspringsample.domain.model.Person
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonListFragment : Fragment(R.layout.fragment_person_list) {

    private lateinit var binding: FragmentPersonListBinding

    private val viewModel: PersonListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonListBinding.bind(view)


        val personItemAdapter = ItemAdapter<PersonListItem>()
        val personFastAdapter = FastAdapter.with(personItemAdapter)
        binding.list.adapter = personFastAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.personList.collect { data ->
                with(viewModel) {
                    personItemAdapter.set(data.map {
                        PersonListItem(
                            it,
                            ::delete
                        )
                    })
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.error.collect { message ->
                if (message != null) {
                    showError(message)
                    Log.e("PersonList", message)
                    viewModel.error.value = null
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect { isLoading ->
                binding.loading.isVisible = isLoading
            }
        }

        personFastAdapter.onClickListener = { _, _, item, _ ->
            showEditDialog(item)
            false
        }

        binding.add.setOnClickListener {
            showCreateDialog()
        }

        viewModel.getAll()
    }

    private fun showError(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showEditDialog(item: PersonListItem) {
        val dialogBinding = EditPersonDialogBinding.inflate(
            layoutInflater, null, false
        )
        dialogBinding.name.setText(item.person.name)
        dialogBinding.email.setText(item.person.email)
        dialogBinding.age.setText(item.person.age.toString())
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.edit_person))
            .setView(dialogBinding.root)
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.apply)) { _, _ ->
                if (dialogBinding.name.text.isNotBlank()
                    && dialogBinding.email.text.isNotBlank()
                    && dialogBinding.age.text.isNotBlank()
                    && dialogBinding.age.text.toString().toInt() >= 0
                ) {
                    viewModel.update(
                        Person(
                            id = item.person.id,
                            name = dialogBinding.name.text.toString(),
                            email = dialogBinding.email.text.toString(),
                            age = dialogBinding.age.text.toString().toInt()
                        )
                    )
                } else showError(resources.getString(R.string.blank_fields_error))
            }
            .show()
    }

    private fun showCreateDialog() {
        val dialogBinding = EditPersonDialogBinding.inflate(
            layoutInflater, null, false
        )
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.add_person))
            .setView(dialogBinding.root)
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.add)) { _, _ ->
                if (dialogBinding.name.text.isNotBlank()
                    && dialogBinding.email.text.isNotBlank()
                    && dialogBinding.age.text.isNotBlank()
                    && dialogBinding.age.text.toString().toInt() >= 0
                ) {
                    viewModel.create(
                        Person(
                            name = dialogBinding.name.text.toString(),
                            email = dialogBinding.email.text.toString(),
                            age = dialogBinding.age.text.toString().toInt()
                        )
                    )
                } else showError(resources.getString(R.string.blank_fields_error))
            }
            .show()
    }
}