package com.dm.berxley.newsapp.presentation.details

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dm.berxley.newsapp.R
import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.domain.models.Source
import com.dm.berxley.newsapp.presentation.Dimens.ArticleImageHeight
import com.dm.berxley.newsapp.presentation.Dimens.MediumPadding1
import com.dm.berxley.newsapp.presentation.details.components.DetailsTopBar
import com.dm.berxley.newsapp.presentation.home.HomeViewModel
import com.dm.berxley.newsapp.ui.theme.NewsAppTheme

@Composable
fun DetailsScreen(
    newsViewModel: HomeViewModel,
    navHostController: NavHostController
) {

    val context = LocalContext.current

//    val newsViewModel = hiltViewModel<HomeViewModel>()

    val homeState = newsViewModel.homeState.collectAsState()

    homeState.value.selectedArticle?.let { article ->
        Scaffold(
            topBar = {
                DetailsTopBar(
                    onBrowsingClick = {
                        Intent(Intent.ACTION_VIEW).also {
                            it.data = Uri.parse(article.url)

                            if (it.resolveActivity(context.packageManager) != null) {
                                context.startActivity(it)
                            }
                        }
                    },
                    onShareClick = {
                        Intent(Intent.ACTION_SEND).also {
                            it.putExtra(Intent.EXTRA_TEXT, article.url)
                            it.type = "text/plain"
                            if (it.resolveActivity(context.packageManager) != null) {
                                context.startActivity(it)
                            }

                        }
                    },
                    onBookmarkClick = { /*TODO*/ },
                    onBackClick = {
                        navHostController.navigateUp()
                    },
                    title = article.title
                )
            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(),
                        start = MediumPadding1,
                        end = MediumPadding1
                    )
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(context = context).data(article.urlToImage)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ArticleImageHeight)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(MediumPadding1))

                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.displaySmall,
                        color = colorResource(
                            id = R.color.text_title
                        )
                    )

                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(
                            id = R.color.body
                        )
                    )
                }

            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailsPrev() {
//    NewsAppTheme {
//        DetailsScreen(
//            navHostController = rememberNavController()
//        )
//    }
//}