package com.example.clientforspringsample.presentation.personlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.clientforspringsample.R
import com.example.clientforspringsample.databinding.FragmentPersonListItemBinding
import com.example.clientforspringsample.domain.model.Person
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class PersonListItem(
    val person: Person,
    val onRemoveListener: (id: Long) -> Unit,
) : AbstractBindingItem<FragmentPersonListItemBinding>() {

    override val type: Int
    get() = R.id.fragment_person_list_item

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentPersonListItemBinding {
        return FragmentPersonListItemBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: FragmentPersonListItemBinding, payloads: List<Any>) {
        binding.id.text = person.id.toString()
        binding.name.text = person.name
        binding.email.text = person.email
        binding.age.text = person.age.toString()
        binding.remove.setOnClickListener {
            onRemoveListener(person.id)
        }
    }
}