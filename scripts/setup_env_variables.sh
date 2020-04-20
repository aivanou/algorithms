#!/bin/sh
tagged_version() {
  # Grabs version from either the env variable CIRCLE_TAG
  if [[ -n "${CIRCLE_TAG:-}" ]]; then
    echo "${CIRCLE_TAG}"
  else
    return 1
  fi
}

BASE_BUILD_VERSION='cat ./version.txt'

if tagged_version >/dev/null; then
  # Grab git tag, remove prefixed v and remove everything after -
  # Used to clean up tags that are for release candidates like v1.6.0-rc1
  # Turns tag v1.6.0-rc1 -> v1.6.0
  TORCHELASTIC_BUILD_VERSION="$(tagged_version)"
fi

# We need to write an envfile to persist these variables to following
# steps, but the location of the envfile depends on the circleci executor
if [[ "$(uname)" == Darwin ]]; then
  # macos executor (builds and tests)
  workdir="/Users/aivanou/code/algorithms"
elif [[ -d "/home/circleci/algorithms" ]]; then
  # machine executor (binary tests)
  workdir="/home/circleci/algorithms"
else
  # docker executor (binary builds)
  workdir="/"
fi
envfile="$workdir/env"
touch "$envfile"
chmod +x "$envfile"

cat >>"$envfile" <<END
# =================== The following code will be executed inside Docker container ===================
export TZ=UTC
echo "Running on $(uname -a) at $(date)"

export BASE_BUILD_VERSION="BASE_BUILD_VERSION"

export CIRCLE_TAG="${CIRCLE_TAG:-}"
export CIRCLE_SHA1="$CIRCLE_SHA1"
export CIRCLE_PR_NUMBER="${CIRCLE_PR_NUMBER:-}"
export CIRCLE_BRANCH="$CIRCLE_BRANCH"
# =================== The above code will be executed inside Docker container ===================
END

echo 'retry () {' >>"$envfile"
echo '    $*  || (sleep 1 && $*) || (sleep 2 && $*) || (sleep 4 && $*) || (sleep 8 && $*)' >>"$envfile"
echo '}' >>"$envfile"
echo 'export -f retry' >>"$envfile"

cat "$envfile"

eval "$envfile"
