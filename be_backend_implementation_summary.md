# BE Backend Implementation Summary

## 작업 목적
`../be` Spring 프로젝트에, 프론트(`src/lib/api/types.ts`) 계약과 맞는 최소 백엔드 골격을 구현했다.

## 구현 범위
- Avatar/Version/Template 도메인 기본 CRUD API
- JPA 엔티티/리포지토리/서비스/컨트롤러 계층
- CORS/보안 최소 설정
- 테스트 환경(H2) 분리
- 빌드 의존성 보완(Jackson)

## 생성/수정 파일

### 1) API 계층
- `../be/src/main/java/unflatten/demo/avatar/api/ApiDtos.java`
- `../be/src/main/java/unflatten/demo/avatar/api/AvatarController.java`
- `../be/src/main/java/unflatten/demo/avatar/api/TemplateController.java`

### 2) 도메인 계층
- `../be/src/main/java/unflatten/demo/avatar/domain/AvatarEntity.java`
- `../be/src/main/java/unflatten/demo/avatar/domain/AvatarVersionEntity.java`
- `../be/src/main/java/unflatten/demo/avatar/domain/TemplateEntity.java`

### 3) Repository 계층
- `../be/src/main/java/unflatten/demo/avatar/repo/AvatarRepository.java`
- `../be/src/main/java/unflatten/demo/avatar/repo/AvatarVersionRepository.java`
- `../be/src/main/java/unflatten/demo/avatar/repo/TemplateRepository.java`

### 4) Service 계층
- `../be/src/main/java/unflatten/demo/avatar/service/AvatarService.java`
- `../be/src/main/java/unflatten/demo/avatar/service/TemplateService.java`

### 5) Config
- `../be/src/main/java/unflatten/demo/avatar/config/SecurityConfig.java`
- `../be/src/main/java/unflatten/demo/avatar/config/JacksonConfig.java`

### 6) 설정/의존성
- 수정: `../be/src/main/resources/application.yaml`
- 추가: `../be/src/test/resources/application.yaml`
- 수정: `../be/build.gradle`
  - `jackson-databind`, `jackson-core` 추가

## API 엔드포인트

### Avatar
- `PUT /api/v1/avatars/{avatarId}`
- `GET /api/v1/avatars/{avatarId}`
- `GET /api/v1/avatars`
- `DELETE /api/v1/avatars/{avatarId}`

### Version
- `POST /api/v1/avatars/{avatarId}/versions`
- `GET /api/v1/avatars/{avatarId}/versions`
- `PATCH /api/v1/avatars/{avatarId}/versions/{versionId}`
- `DELETE /api/v1/avatars/{avatarId}/versions/{versionId}`

### Template
- `GET /api/v1/templates`
- `GET /api/v1/templates/{templateId}`

## 주요 구현 포인트
- 프론트 타입 계약을 유지하기 위해 `parameters`를 JSON(Text)로 저장.
- 버전 저장 시 서버에서 최대 5개 제한 정책 적용.
- 템플릿은 초기 데이터 1건(`customizable-default`) 자동 시드.
- API 경로(`/api/**`)는 개발 편의상 permitAll, CORS 허용(3000 포트).
- 테스트는 H2 메모리 DB로 동작하도록 별도 설정 분리.

## 검증 결과
실행 명령:
```bash
cd ../be && ./gradlew test --no-daemon
```
결과: `BUILD SUCCESSFUL`

## 남은 작업(권장)
1. 프론트 `remote.ts` 구현 후 `local.ts` 대체 연결
2. 인증/인가(JWT) 적용 및 `/api/**` 접근 제어 강화
3. Flyway 도입으로 스키마 이력 관리
4. OpenAPI(springdoc) 추가 및 API 문서 자동화
