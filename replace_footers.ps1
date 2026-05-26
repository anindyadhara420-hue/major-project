$files = @(
  "admissions.html",
  "pages\academic\learning.html",
  "pages\academic\program.html",
  "pages\explore\experience.html",
  "pages\explore\faculty.html",
  "pages\explore\mission.html",
  "pages\facilities\laboratory.html",
  "pages\facilities\libraries.html",
  "pages\facilities\smart_class.html",
  "pages\facilities\soft_skills.html"
)

foreach ($file in $files) {
  $path = "d:\STS\mejor-project-techno\src\main\resources\templates\$file"
  if (Test-Path $path) {
    $content = Get-Content -Raw -Path $path
    $newContent = $content -replace '(?s)<footer>.*?</footer>', '<footer th:replace="~{fragments/footer :: footer}"></footer>'
    Set-Content -Path $path -Value $newContent
    Write-Host "Replaced footer in $file"
  } else {
    Write-Host "File not found: $file"
  }
}
