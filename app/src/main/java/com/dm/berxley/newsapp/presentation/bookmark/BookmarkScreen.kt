package com.dm.berxley.newsapp.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dm.berxley.newsapp.R
import com.dm.berxley.newsapp.presentation.Dimens
import com.dm.berxley.newsapp.presentation.Dimens.MediumPadding1
import com.dm.berxley.newsapp.presentation.home.components.ArticleCard

@Composable
fun BookmarkScreen(
    navHostController: NavHostController
) {
    val viewModel = hiltViewModel<BookmarkViewModel>()
    val bookMarkState = viewModel.articleState.collectAsState().value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = MediumPadding1,
                    end = MediumPadding1
                )
        ) {
            Text(
                text = "Bookmark",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = colorResource(
                    id = R.color.text_title
                )
            )

            Spacer(modifier = Modifier.height(MediumPadding1))

            if (bookMarkState.articles.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your bookmarks will appear here",
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MediumPadding1),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding2)
                ) {
                    items(bookMarkState.articles.size) { index ->
                        ArticleCard(article = bookMarkState.articles[index]) {
                            // navHostController.navigate(Screen.DetailsScreen.route)
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookMarkPrev() {
    BookmarkScreen(navHostController = rememberNavController())
}
