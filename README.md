# Verve

Verve is an extremely lightweight framework for making toy apps.

## Quickstart

Define an `Animation` class with corresponding `Input`, `Parameters`, and `Renderer`. Attach
a `android.view.SurfaceView` from your UI layer to an `AnimationRunner` and before you know it
you've got your animation's main loop that handles updating and rendering.

## AnimationRunner

Create an instance of this class to hold your `Animation`, attach or detach a `SurfaceView`, and
handle Activity lifecycle changes.

## Animation

The magic happens in `Animation.update(dt)` - this function is responsible for updating the world
and all objects therein.

## Inputs

Some handy out-of-the-box inputs are available for manipulating your animation:

- `PressDetector` for keeping track of stationary touch events
- `SwipeDetector` for detecting and processing swipes on the screen
- `RotationDetector` for detecting the device's orientation

Attach these to your `AnimationInput` class and read from them as you update your animation.

## Rendering

Draw on your `android.graphics.Canvas` in the `AnimationRenderer.updateCanvas(Canvas)` by reading
the latest state of the properties given to it by the `Animation`. Perhaps use a `Camera` if you
want maximum versatility, such as moving around in 3D space.

## Maths

Make use of the mathematical tools package either for computing physics in the world or for
preparing and rendering the world objects.
