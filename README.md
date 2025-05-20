<image width="100" alt="image" src="https://github.com/user-attachments/assets/1ce893dc-f8a0-4017-91f5-023623f540a9">

## 전시회닷 - 세상의 전시를 잇다

|<img src="https://github.com/user-attachments/assets/8cdf5ba0-20ff-46ad-aee2-241711de833b" width = 200>|<img src="https://github.com/user-attachments/assets/d5983306-61c1-4c6f-96d4-65a628cf5f93" width = 200>|<img src="https://github.com/user-attachments/assets/ccc5cb7d-8b83-487c-9a26-d57bb3d6c3e8" width = 200>|
|:-:|:-:|:-:|
|로그인|홈 - 필터링|홈 - 검색|

|<img src="https://github.com/user-attachments/assets/55090709-d8a0-49d1-a387-2d7cd50bbc8c" width = 200>|<img src="https://github.com/user-attachments/assets/6eb649aa-0fa0-4899-84ae-7723b83e64a1" width = 200>|<img src="https://github.com/user-attachments/assets/e3533912-f9f9-42bd-9222-45b334089ccf" width = 200>|
|:-:|:-:|:-:|
|이벤트 추가|상세 - 애니메이션|상세 - 댓글쓰기|

<br><br>

## 아키텍처

![전시회닷 앱 구조](https://github.com/user-attachments/assets/20d80648-a4b5-419c-bd8e-1aa0e625f45a)

<br><br>

## 고려사항

**1. 이미지 다운샘플링**

- [블로그 - 이미지 전송을 위한 Uri to File (with Result)](https://velog.io/@rio319/Android-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%A0%84%EC%86%A1%EC%9D%84-%EC%9C%84%ED%95%9C-Uri-to-File-with-Result)
- [코드 - ImageProcessor.kt](https://github.com/AppLinkers/ExhibitionDoT-Android/blob/main/presentation/src/main/java/com/exhibitiondot/presentation/util/ImageProcessor.kt)

<br>

**2. runCathing을 이용한 예외 넘기기**
```kotlin
class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(email: String): Result<Unit> = runCatching {
        val userId = userRepository.signIn(email)
            .getOrElse { exception ->
                when (exception) {
                    is NetworkFailException -> throw exception
                    else -> throw SignInFailException(exception)
                }
            }
        preferenceRepository.updateUserId(userId)
    }
}
```

<br>

**3. Filter interface 분리를 통한 확장성 확보**
```kotlin
// Filter.kt (domain)
interface Filter {
    val key: String
}

interface SingleFilter : Filter {
    val displayName: String
}

interface MultiFilter : Filter

enum class Region(
    override val key: String,
    override val displayName: String,
) : SingleFilter

enum class Category(override val key: String) : MultiFilter

enum class EventType(override val key: String) : MultiFilter

// FilterState.kt (presentation)
interface IFilterState<T : Filter> {
    val filterList: List<T>
    fun selectFilter(filter: T)
    fun resetAll()
}

interface ISingleFilterState<T : SingleFilter> : IFilterState<T> {
    var selectedFilter: T?
    fun setFilter(filter: T?)
}

interface IMultiFilerState<T : MultiFilter> : IFilterState<T> {
    var selectedFilterList: List<T>
    fun setFilter(filterList: List<T>)
}
```
<br>

**4. 전략 패턴을 활용한 유연한 DateFormat 매핑**

- [코드 - DateFormatStrategy](https://github.com/AppLinkers/ExhibitionDoT-Android/blob/main/presentation/src/main/java/com/exhibitiondot/presentation/mapper/DateMapper.kt)
```kotlin
// EventMapper.kt (presentation)
fun EventDetail.toUiModel() =
    EventDetailUiModel(
        id = id,
        name = name,
        imgUrl = imgUrl,
        region = region.name,
        categoryTags = categoryList.joinToString(" ", transform = Category::toTag),
        eventTypeTags = eventTypeList.joinToString(" ", transform = EventType::toTag),
        date = format(DateFormatStrategy.FullDate(date)),
        createdAt = format(DateFormatStrategy.RelativeTime(createdAt)),
        likeCount = likeCount,
        isLike = isLike,
        owner = owner
    )
```

<br>

**5. derivedState을 통해 애니메이션 flag의 Recomposition 줄이기**
```kotlin
@Composable
private fun EventDetailScreen(
    modifier: Modifier,
    eventDetail: EventDetailUiModel,
    ...
) {
    val lazyListState = rememberLazyListState()
    val skipImage by remember {
        derivedStateOf {
            (lazyListState.firstVisibleItemIndex == 0 &&
                    lazyListState.firstVisibleItemScrollOffset < 1100).not()
        }
    }
    ...
    EventDetailTopBar(     
        skipImage = skipImage,
        ...
    )
}

@Composable
fun EventDetailTopBar(
    skipImage: Boolean,
    ...
) {
    val containerColor by animateColorAsState(
        targetValue = if (skipImage) {
            MaterialTheme.colorScheme.background
        } else {
            Color.Transparent
        },
        label = "event-detail-top-bar-container-color-anim"
    )
    val iconColor by animateColorAsState(
        targetValue = if (skipImage) {
            MaterialTheme.colorScheme.surfaceContainerHigh
        } else {
            MaterialTheme.colorScheme.background
        },
        label = "event-detail-top-bar-icon-color-anim"
    )
    ...
}
```
