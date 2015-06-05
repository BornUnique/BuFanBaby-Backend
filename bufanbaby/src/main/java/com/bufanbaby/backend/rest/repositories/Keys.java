package com.bufanbaby.backend.rest.repositories;

/**
 * The keys are used to manipulate the data in Redis.
 */
public abstract class Keys {

	/********************* User-related keys *****************************/

	public static String nextUserId() {
		return "global:next_user_id";
	}

	// Set: all users ids
	// users -> {1234, 5678, 90}
	public static String usersKey() {
		return "users";
	}

	// Hash: store user info
	// user:{1234} -> {name: laiyan, age: 20}
	public static String userIdKey(String userId) {
		return "users:" + userId;
	}

	// user:{laiyan}:id = {1}
	// string key = string value
	public static String usernameIdKey(String username) {
		return "users:" + username + ":id";
	}

	// user:{id}:followers = {followers' user ids}
	// set
	public static String userIdFollowersKey(String userId) {
		return "users:" + userId + ":followers";
	}

	// user:{id}:following = {users' ids followed}
	// set
	public static String userIdFollowingKey(String userId) {
		return "users:" + userId + ":following";
	}

	/********************* Moment-related keys *****************************/

	public static String nextMomentIdKey() {
		return "global:next_moment_id";
	}

	public static String nextImageIdKey() {
		return "global:next_image_id";
	}

	public static String nextDocumentIdKey() {
		return "global:next_document_id";
	}

	public static String nextAudioIdKey() {
		return "global:next_audio_id";
	}

	public static String nextVideoIdKey() {
		return "global:next_video_id";
	}

	// moment:id ={feeling: "", time: ""}
	// hash
	public static String momentIdKey(long momentId) {
		return "moments:" + momentId;
	}

	// This moment's tags
	// moment:{id}:tags={kaiqin, laiyan, sydney, angie}
	// set
	public static String momentIdTagsKey(long momentId) {
		return "moments:" + momentId + ":tags";
	}

	// all user's own moments
	// user:{id}:moments={1, 2, 3, 4}
	// list
	public static String userIdMomentsKey(long userId) {
		return "users:" + userId + ":moments";
	}

	// users' moments (public + friends scope) + following's (Friends + business
	// account) + Others' public + pushed from Company
	// limitation: 1000 items
	public static String userIdTimelineKey(long userId) {
		return "users:" + userId + ":timeline";
	}

	public static String userIdTagTimelineKey(long userId, String tag) {
		return "users:" + userId + ":" + tag + ":timeline";
	}

	// global:timeline ={1, 2, 3}
	// list: 500 latest moments from shared to world
	public static String globalTimelineKey() {
		return "global:timeline";
	}

	public static String imagesIdKey(long imageId) {
		return "images:" + imageId;
	}

	public static String documentsIdKey(long documentId) {
		return "documents:" + documentId;
	}

	public static String audiosIdKey(long audioId) {
		return "audios:" + audioId;
	}

	public static String videosIdKey(long videoId) {
		return "videos:" + videoId;
	}

	public static String momentsIdDocumentsKey(long momentId) {
		return "moments:" + momentId + ":documents";
	}

	public static String momentsIdImagessKey(long momentId) {
		return "moments:" + momentId + ":images";
	}

	public static String momentsIdAudiosKey(long momentId) {
		return "moments:" + momentId + ":audios";
	}

	public static String momentsIdVideosKey(long momentId) {
		return "moments:" + momentId + ":videos";
	}
}
