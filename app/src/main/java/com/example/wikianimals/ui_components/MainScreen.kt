package com.example.wikianimals.ui_components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.wikianimals.utils.DrawerEvents
import com.example.wikianimals.utils.IdArrayList
import com.example.wikianimals.utils.ListItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context, onClick: (ListItem) -> Unit) {
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    var topBarTitle by remember { mutableStateOf("") }
    val coroutine = rememberCoroutineScope()
    var mainList by remember {
        mutableStateOf(getListItemsByIndex(0, context))
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerMenu() { event ->
                    when (event) {
                        is DrawerEvents.OnItemClick -> {
                            topBarTitle = event.title
                            mainList = getListItemsByIndex(event.index, context)
                        }
                    }
                    coroutine.launch {
                        drawerState.close()
                    }

                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                MainTopBar(
                    title = topBarTitle, drawerState
                )
            },

            ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(mainList) { item ->
                    MainListItem(item = item) {listItem ->
                        onClick(listItem)
                    }
                }
            }
        }
    }
}


private fun getListItemsByIndex(index: Int, context: Context): List<ListItem> {
    val list = ArrayList<ListItem>()
    val arrayList = context.resources.getStringArray(IdArrayList.listId[index])

    arrayList.forEach {
        val item = it.split("|")
        list.add(
            ListItem(
                title = item[0],
                imageName = item[1],
                htmlName = item[2]
            )
        )
    }
    return list
}