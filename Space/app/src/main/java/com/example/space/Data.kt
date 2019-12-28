package com.example.space

data class Data(
    var alt_description: Any,
    var categories: List<Any>,
    var color: String,
    var created_at: String,
    var current_user_collections: List<Any>,
    var description: Any,
    var height: Int,
    var id: String,
    var liked_by_user: Boolean,
    var likes: Int,
    var links: Links,
    var sponsored: Boolean,
    var sponsored_by: Any,
    var sponsored_impressions_id: Any,
    var updated_at: String,
    var urls: Urls,
    var user: User,
    var width: Int
)

data class Links(
    var download: String,
    var download_location: String,
    var html: String,
    var self: String
)

data class Urls(
    var full: String,
    var raw: String,
    var regular: String,
    var small: String,
    var thumb: String
)

data class User(
    var accepted_tos: Boolean,
    var bio: String,
    var first_name: String,
    var id: String,
    var instagram_username: String,
    var last_name: String,
    var links: LinksX,
    var location: String,
    var name: String,
    var portfolio_url: String,
    var profile_image: ProfileImage,
    var total_collections: Int,
    var total_likes: Int,
    var total_photos: Int,
    var twitter_username: String,
    var updated_at: String,
    var username: String
)

data class LinksX(
    var followers: String,
    var following: String,
    var html: String,
    var likes: String,
    var photos: String,
    var portfolio: String,
    var self: String
)

data class ProfileImage(
    var large: String,
    var medium: String,
    var small: String
)