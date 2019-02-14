using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Linq;

public class Game : MonoBehaviour {

    public static int gridWidth = 4, gridHeight = 4;

    public static Transform[,] grid = new Transform[gridWidth, gridHeight];

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        CheckUserInput();
	}

    void CheckUserInput() {
        bool down = Input.GetKeyDown(KeyCode.DownArrow), up = Input.GetKeyDown(KeyCode.UpArrow), left = Input.GetKeyDown(KeyCode.LeftArrow), right = Input.GetKeyDown(KeyCode.RightArrow);

        if (down || up || left || right) {

            if (down) {
                Debug.Log("Player pressed Down");
            }

            if (up) {
                Debug.Log("Player pressed up");
            }

            if (left) {
                Debug.Log("Player pressed left");
            }

            if (right) {
                Debug.Log("Player pressed right");
            }

        }

    }

    void GenerateNewTile(int howMany) {
        for () {

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


    public void PlayAgain() {
        grid = new Transform[gridWidth, gridHeight];
    }

}
