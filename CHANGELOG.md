# Changelog

All notable changes to this project are documented here.

The format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project uses [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-09-27

First release. The library was written against formio.js 4.x and had never been
released; this version supports formio.js 5.x as well.

### Added

- `FormioRuntime` picks the formio.js line a generated page loads: 4.21.2 from
  cdn.form.io, 5.6.1 from the `@formio/js` npm package through jsdelivr,
  because Form.io stopped publishing 5.x to its own CDN. Each line brings its
  own Bootstrap flavour.
- The components formio gained since 2021: `EmailComponent`, `UrlComponent`,
  `PhoneNumberComponent`, `PasswordComponent`, `CurrencyComponent`,
  `TagsComponent`, `HiddenComponent`, `WellComponent`,
  `HtmlElementComponent`, `RadioComponent`, `SelectBoxesComponent`,
  `EditGridContainer`, `TimeComponent` and `DayComponent`.
- `PartialDate`, the value a `DayComponent` hands back, where a part the user
  did not choose is zero. A day arrives as a slash separated string whose
  order follows the component's `dayFirst`, and the component reads the same
  property to parse it.
- Shared base classes `StringComponent`, `NumberComponent`,
  `OptionComponent` and `SubFormArrayContainer`, so the datagrid and the
  editgrid, the select and the radio, and the number and the currency share
  their behaviour instead of each repeating it.
- A browser test that renders a generated page on each formio.js line, fills it
  in, submits it, and feeds the posted JSON back through the Java validator. It
  also checks that the browser and the Java validator enforce the same length,
  pattern and range rules, so the two cannot drift apart unnoticed.
- Apache License 2.0, with a NOTICE crediting Form.io's MIT licensed bundles.

### Fixed

Each of these let the library accept something it should not have.

- Plain text values are no longer HTML escaped. The sanitiser escaped whatever
  it kept, so an email address came back as `ada&#64;example.com`, which
  corrupted the stored value and made any pattern mentioning an at sign
  impossible to match. It now runs only when the value contains a `<`, the
  only character that can open a tag. `WysiwygComponent` is unchanged and
  still always sanitises.
- An error inside a `datagrid` or an `editgrid` is reported. A required
  field missing inside a row was recorded in the flat report but never reached
  `FormResponse.isErrorPresent()`, so the submission was accepted.
  `getErrorMessage` now names the entry, for example `members123[1]/who123`.
- A repeating section submitted as `null`, or as anything that is not an
  array, no longer throws an uncaught `ClassCastException`.
- `required` and the item count mean something on a repeating section.
  `required` now marks the section as well as its fields, so a section can be
  required at all, and `minItems` on a datagrid writes `validate.minItems`
  rather than `validate.minLength`, a text length rule on a row count.
- Generated pages load from jsdelivr and are pinned to a formio.js version,
  rather than an unversioned CDN URL and a discontinued one.

### Changed

- Requires Java 21 rather than Java 11.
- A generated page runs in English and carries the Italian translation as well.
  The server side messages are localised separately, through the
  `response_error*.properties` bundles.
- `EnumComponent` and `DataGridContainer` are now thin classes over the
  shared bases. Their behaviour is unchanged.

[1.0.0]: https://github.com/fillumina/formio-gen/releases/tag/1.0.0
