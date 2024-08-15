package com.dm.berxley.newsapp.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.dm.berxley.newsapp.R
import com.dm.berxley.newsapp.domain.models.Article
import com.dm.berxley.newsapp.domain.models.Source
import com.dm.berxley.newsapp.presentation.Dimens.ArticleCardSize
import com.dm.berxley.newsapp.presentation.Dimens.ExtraSmallPadding
import com.dm.berxley.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.dm.berxley.newsapp.presentation.Dimens.SmallIconSize
import com.dm.berxley.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Row(modifier = modifier.clickable { onClick() }) {

        AsyncImage(
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop,
            model = article.urlToImage,
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding)
                .height(ArticleCardSize),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body),
                )

                Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                Icon(
                    modifier = Modifier.size(SmallIconSize),
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = null, tint = colorResource(
                        id = R.color.body
                    )
                )

                Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                Text(
                    text = article.publishedAt,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body),
                )


            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ArticlePrev() {
        ArticleCard(
            article = Article(
                author = "Lorem Ipsum",
                content = "Lorem Ipsum Lorem Ipsum Lorem Ipsum",
                description = "Lorem Ipsum, Lorem Ipsum, Lorem Ipsum, Lorem Ipsum",
                publishedAt = "2 hours ago",
                source = Source(id = "source", name = "BBC"),
                title = "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
                url = "",
                urlToImage = ""
            )
        ) {

        }

}