﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Linq;

public class Game : MonoBehaviour {

    public static int gridWidth = 4, gridHeight = 4;

    public static Transform[,] grid = new Transform[gridWidth, gridHeight];

	// Use this for initialization
	void Start () {
        GenerateNewTile(2);
	}
	
	// Update is called once per frame
	void Update () {
        bool isGameOver = CheckGameOver();

        if (isGameOver != true) {
            CheckUserInput();
        }

    }

    void CheckUserInput() {
        bool down = Input.GetKeyDown(KeyCode.DownArrow), up = Input.GetKeyDown(KeyCode.UpArrow), left = Input.GetKeyDown(KeyCode.LeftArrow), right = Input.GetKeyDown(KeyCode.RightArrow);

        if (down || up || left || right) {
            PrepareTilesForMerging();
            if (down) {
                Debug.Log("Player pressed Down");
                MoveAllTiles(Vector2.down);
            }

            if (up) {
                Debug.Log("Player pressed up");
                MoveAllTiles(Vector2.up);
            }

            if (left) {
                Debug.Log("Player pressed left");
                MoveAllTiles(Vector2.left);
            }

            if (right) {
                Debug.Log("Player pressed right");
                MoveAllTiles(Vector2.right);
            }

        }

    }

    bool CheckGameOver() {
        if (transform.childCount < gridWidth * gridHeight) {
            return false;
        }

        for (int x = 0; x < gridWidth; x++)
        {
            for (int y = 0; y < gridHeight; y++)
            {
                Transform currentTile = grid[x, y];
                Transform tileBelow = null;
                Transform tileBeside = null;

                if (y != 0) {
                    tileBelow = grid[x, y - 1];
                }

                if (x != gridWidth - 1) {
                    tileBeside = grid[x + 1, y];
                }

                if (tileBeside != null) {
                    if (currentTile.GetComponent<Tile>().tileValue == tileBeside.GetComponent<Tile>().tileValue) {
                        return false;
                    }
                }

                if (tileBelow != null)
                {
                    if (currentTile.GetComponent<Tile>().tileValue == tileBelow.GetComponent<Tile>().tileValue)
                    {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    void MoveAllTiles(Vector2 direction) {
        int tilesMovedCount = 0;
        if (direction == Vector2.left){
            for (int x = 0; x < gridWidth; x++) {
                for (int y = 0; y < gridHeight; y++) {
                    if (grid[x,y] != null) {
                        if (MoveTile(grid[x, y], direction)) {
                            tilesMovedCount++;
                        }
                    }
                }
            }

        }

        if (direction == Vector2.right)
        {
            for (int x = gridWidth-1; x >= 0; x--)
            {
                for (int y = 0; y < gridHeight; y++)
                {
                    if (grid[x, y] != null)
                    {
                        if (MoveTile(grid[x, y], direction))
                        {
                            tilesMovedCount++;
                        }
                    }
                }
            }
        }

        if (direction == Vector2.down)
        {
            for (int x = 0; x < gridWidth; x++)
            {
                for (int y = 0; y < gridHeight-1; y++)
                {
                    if (grid[x, y] != null)
                    {
                        if (MoveTile(grid[x, y], direction))
                        {
                            tilesMovedCount++;
                        }
                    }
                }
            }
        }

        if (direction == Vector2.up)
        {
            for (int x = 0; x < gridWidth; x++)
            {
                for (int y = gridHeight-1; y >= 0; y--)
                {
                    if (grid[x, y] != null)
                    {
                        if (MoveTile(grid[x, y], direction))
                        {
                            tilesMovedCount++;
                        }
                    }
                }
            }

        }

        if (tilesMovedCount != 0) {
            GenerateNewTile(1);
        }

    }

    bool MoveTile(Transform tile, Vector2 direction) {
        Vector2 startPos = tile.localPosition;

        while(true) {
            tile.transform.localPosition += (Vector3)direction;
            Vector2 pos = tile.transform.localPosition;
            if (CheckIsInsideGrid(pos)) {
                if (CheckIsAtValidPosition(pos))
                {
                    UpdateGrid();
                }
                else {

                    if (!CheckAndCombineTiles(tile))
                    {

                        tile.transform.localPosition += -(Vector3)direction;

                        if (tile.transform.localPosition == (Vector3)startPos)
                        {
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }

                }
            } else {
                tile.transform.localPosition += -(Vector3)direction;

                if (tile.transform.localPosition == (Vector3)startPos)
                {
                    return false;
                }
                else {
                    return true;
                }
            }

        }
    }

    bool CheckAndCombineTiles(Transform movingTile) {
        Vector2 pos = movingTile.transform.localPosition;

        Transform collidingTile = grid[(int)pos.x,(int)pos.y];

        int movingTileValue = movingTile.GetComponent<Tile>().tileValue;
        int collidingTileValue = collidingTile.GetComponent<Tile>().tileValue;

        if (movingTileValue == collidingTileValue && !movingTile.GetComponent<Tile>().mergedThisTurn && !collidingTile.GetComponent<Tile>().mergedThisTurn) {
            Destroy(movingTile.gameObject);
            Destroy(collidingTile.gameObject);

            grid[(int)pos.x, (int)pos.y] = null;

            string newTileName = "tile_" + movingTileValue * 2;

            GameObject newTile = (GameObject)Instantiate(Resources.Load(newTileName, typeof(GameObject)), pos, Quaternion.identity);

            newTile.transform.parent = transform;

            newTile.GetComponent<Tile>().mergedThisTurn = true;

            UpdateGrid();

            return true;
        }

        return false;
    }

    void GenerateNewTile(int howMany) {
        for (int i = 0; i < howMany; ++i) {
            Vector2 locationForNewTile = GetRandomLocationForNewTile();

            string tile = "tile_2";

            float chanceOfTwo = Random.Range(0f,1f);

            if (chanceOfTwo > 0.9f) {
                tile = "tile_4";
            }

            GameObject newTile = (GameObject)Instantiate(Resources.Load(tile,typeof(GameObject)), locationForNewTile, Quaternion.identity);

            newTile.transform.parent = transform;
        }

        UpdateGrid();

    }

    void UpdateGrid() {
        for (int y = 0; y < gridHeight; ++y) {
            for (int x = 0; x < gridWidth; ++x) {
                if (grid[x,y] != null) {
                    if (grid[x,y].parent == transform) {
                        grid[x, y] = null;

                    }
                }
            }
        }

        foreach (Transform tile in transform) {
            Vector2 v = new Vector2(Mathf.Round(tile.position.x), Mathf.Round(tile.position.y));

            grid[(int)v.x,(int)v.y] = tile;
        }


    }



    Vector2 GetRandomLocationForNewTile() {
        List<int> x = new List<int>();
        List<int> y = new List<int>();

        for (int j = 0; j < gridWidth; j++) {
            for (int i = 0; i < gridHeight; i++) {
                if (grid[j, i] == null) {
                    x.Add(j);
                    y.Add(i);
                }
            }
        }

        int randIndex = Random.Range(0, x.Count);

        int randX = x.ElementAt(randIndex);
        int randY = y.ElementAt(randIndex);

        return new Vector2(randX,randY);
    }

    bool CheckIsInsideGrid(Vector2 pos) {
        if (pos.x >= 0 && pos.x <= gridWidth -1 && pos.y <= gridHeight -1 && pos.y >= 0) {
            return true;
        }

        return false;
    }

    bool CheckIsAtValidPosition(Vector2 pos) {
        if (grid[(int)pos.x, (int)pos.y] == null) {
            return true;
        }
        return false;
    }

    void PrepareTilesForMerging() {
        foreach (Transform t in transform) {
            t.GetComponent<Tile>().mergedThisTurn = false;
        }
    }


    public void PlayAgain() {
        grid = new Transform[gridWidth, gridHeight];
    }

}
